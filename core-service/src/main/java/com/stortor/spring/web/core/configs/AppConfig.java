package com.stortor.spring.web.core.configs;

import com.stortor.spring.web.core.properties.AnalyticsServiceIntegrationProperties;
import com.stortor.spring.web.core.properties.AnalyticsServiceTimeoutProperties;
import com.stortor.spring.web.core.properties.CartServiceIntegrationProperties;
import com.stortor.spring.web.core.properties.CastServiceTimeoutProperties;
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
@EnableConfigurationProperties(
        {CartServiceIntegrationProperties.class, AnalyticsServiceIntegrationProperties.class, AnalyticsServiceTimeoutProperties.class,
                CastServiceTimeoutProperties.class}
)
@RequiredArgsConstructor
public class AppConfig {
    private final CartServiceIntegrationProperties cartServiceIntegrationProperty;
    private final CastServiceTimeoutProperties castServiceTimeoutProperties;
    private final AnalyticsServiceIntegrationProperties analyticsServiceIntegrationProperties;
    private final AnalyticsServiceTimeoutProperties analyticsServiceTimeoutProperties;

    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, castServiceTimeoutProperties.getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(castServiceTimeoutProperties.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(castServiceTimeoutProperties.getWrite(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(cartServiceIntegrationProperty.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient analyticsServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, analyticsServiceTimeoutProperties.getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(analyticsServiceTimeoutProperties.getRead(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(analyticsServiceTimeoutProperties.getWrite(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(analyticsServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
