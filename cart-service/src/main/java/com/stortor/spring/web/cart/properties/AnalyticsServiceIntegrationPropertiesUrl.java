package com.stortor.spring.web.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.analytics-service")
@Data
public class AnalyticsServiceIntegrationPropertiesUrl {
    private String url;
}
