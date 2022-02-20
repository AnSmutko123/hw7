package com.stortor.spring.web.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.analytics-service.timeouts")
@Data
public class AnalyticsServiceTimeoutProperties {
    private Integer read;
    private Integer write;
    private Integer connection;
}
