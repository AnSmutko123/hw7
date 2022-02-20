package com.stortor.spring.web.analytics.configs;

import com.stortor.spring.web.analytics.properties.CoreServiceIntegrationPropertiesUrl;
import com.stortor.spring.web.analytics.properties.CoreServicePropertiesTimeout;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
        {CoreServiceIntegrationPropertiesUrl.class,
                CoreServicePropertiesTimeout.class}
)
@RequiredArgsConstructor
public class AppConfig {
    private final CoreServiceIntegrationPropertiesUrl coreServiceIntegrationPropertiesUrl;
    private final CoreServicePropertiesTimeout coreServicePropertiesTimeout;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, coreServicePropertiesTimeout.getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(coreServicePropertiesTimeout.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(coreServicePropertiesTimeout.getWrite(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(coreServiceIntegrationPropertiesUrl.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
