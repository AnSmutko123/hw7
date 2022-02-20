package com.stortor.spring.web.analytics.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service.timeouts")
@Data
public class CoreServicePropertiesTimeout {
    private Integer read;
    private Integer write;
    private Integer connection;
}
