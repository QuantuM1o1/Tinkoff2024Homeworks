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

    @NotNull
    AccessType databaseAccessType,

    @NotNull
    int linksToUpdate,

    RetryPolicy retryPolicy
) {
}
