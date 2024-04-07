package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String gitHubBaseUrl,

    @NotEmpty
    String stackOverflowBaseUrl,

    @NotEmpty
    String botUrl,

    @NotEmpty
    String kafkaUrl,

    boolean useQueue
) {
}
