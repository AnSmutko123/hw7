package com.stortor.spring.web.cart.configs;

import com.stortor.spring.web.cart.properties.AnalyticsServiceIntegrationPropertiesTimeout;
import com.stortor.spring.web.cart.properties.AnalyticsServiceIntegrationPropertiesUrl;
import com.stortor.spring.web.cart.properties.CoreServiceIntegrationPropertiesTimeout;
import com.stortor.spring.web.cart.properties.CoreServiceIntegrationPropertiesUrl;
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
public class AppConfig {
    @Value("${integrations.core-service.url}")
    private String coreServiceUrl;

    @Bean
    public WebClient coreServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(coreServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Value("${integrations.analytics-service.url}")
    private String analyticsProductsServiceUrl;

    @Bean
    public WebClient analyticsProductsServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(analyticsProductsServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

}
