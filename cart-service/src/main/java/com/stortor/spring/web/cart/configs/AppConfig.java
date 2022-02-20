package com.stortor.spring.web.cart.configs;

import com.stortor.spring.web.cart.properties.AnalyticsServiceIntegrationPropertiesTimeout;
import com.stortor.spring.web.cart.properties.AnalyticsServiceIntegrationPropertiesUrl;
import com.stortor.spring.web.cart.properties.CoreServiceIntegrationPropertiesTimeout;
import com.stortor.spring.web.cart.properties.CoreServiceIntegrationPropertiesUrl;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;


import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(
        {AnalyticsServiceIntegrationPropertiesUrl.class, AnalyticsServiceIntegrationPropertiesTimeout.class,
                CoreServiceIntegrationPropertiesTimeout.class, CoreServiceIntegrationPropertiesUrl.class}
)
public class AppConfig {

    private final AnalyticsServiceIntegrationPropertiesUrl analyticsServiceUrl;
    private final AnalyticsServiceIntegrationPropertiesTimeout analyticsServiceTimeout;
    private final CoreServiceIntegrationPropertiesUrl cartServiceUrl;
    private final CoreServiceIntegrationPropertiesTimeout coreServiceTimeout;

    @Bean
    public WebClient coreServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, coreServiceTimeout.getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(coreServiceTimeout.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(coreServiceTimeout.getWrite(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(cartServiceUrl.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient analyticsProductsServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, analyticsServiceTimeout.getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(analyticsServiceTimeout.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(analyticsServiceTimeout.getWrite(), TimeUnit.MILLISECONDS));
                });
        return WebClient
                .builder()
                .baseUrl(analyticsServiceUrl.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

}
