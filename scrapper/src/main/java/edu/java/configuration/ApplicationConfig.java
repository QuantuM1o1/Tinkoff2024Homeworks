package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String botUrl,

    @NotEmpty
    String gitHubBaseUrl,

    @NotEmpty
    String stackOverflowBaseUrl,

    @NotNull
    AccessType databaseAccessType,

    @NotNull
    int linksToUpdate
) {

    @NotEmpty
    String botUrl,

    @NotEmpty
    String kafkaUrl,

    boolean useQueue
) {
}
