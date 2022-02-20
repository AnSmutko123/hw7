package com.stortor.spring.web.analytics.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

// Добавить микросервис рекомендаций
// - 5 наиболее покупаемых продуктов за месяц
// - 5 наиболее складываемых продуктов в корзину за день
// - вывести на главной странице эти рекомендации в виде текста

@Configuration
public class AppConfig {
    @Value("${integrations.core-service.url}")
    private String cartServiceUrl;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(cartServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
//
//import com.stortor.spring.web.analytics.properties.CoreServiceIntegrationPropertiesTimeout;
//        import com.stortor.spring.web.analytics.properties.CoreServiceIntegrationPropertiesUrl;
//        import io.netty.channel.ChannelOption;
//        import io.netty.handler.timeout.ReadTimeoutHandler;
//        import io.netty.handler.timeout.WriteTimeoutHandler;
//        import lombok.RequiredArgsConstructor;
//        import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.boot.context.properties.EnableConfigurationProperties;
//        import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//        import org.springframework.web.reactive.function.client.WebClient;
//        import reactor.netty.http.client.HttpClient;
//        import reactor.netty.tcp.TcpClient;
//
//        import java.util.concurrent.TimeUnit;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableConfigurationProperties(
//        {CoreServiceIntegrationPropertiesTimeout.class, CoreServiceIntegrationPropertiesUrl.class}
//)
//public class AppConfig {
//    private final CoreServiceIntegrationPropertiesTimeout coreServiceIntegrationPropertiesTimeout;
//    private final CoreServiceIntegrationPropertiesUrl coreServiceIntegrationPropertiesUrl;
//
//
//    @Bean
//    public WebClient coreServiceWebClient() {
//        TcpClient tcpClient = TcpClient
//                .create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, coreServiceIntegrationPropertiesTimeout.getConnection())
//                .doOnConnected(connection -> {
//                    connection.addHandlerLast(new ReadTimeoutHandler(coreServiceIntegrationPropertiesTimeout.getRead(), TimeUnit.MILLISECONDS));
//                    connection.addHandlerLast(new WriteTimeoutHandler(coreServiceIntegrationPropertiesTimeout.getWrite(), TimeUnit.MILLISECONDS));
//                });
//
//        return WebClient
//                .builder()
//                .baseUrl(coreServiceIntegrationPropertiesUrl.getUrl())
//                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
//                .build();
//    }
//}
