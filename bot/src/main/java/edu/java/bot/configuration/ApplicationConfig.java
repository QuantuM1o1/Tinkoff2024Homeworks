package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String scrapperUrl,

    @NotEmpty
    String telegramToken,

    @NotNull
    RetryType retryType,

    @NotNull
    int retryAttempts,

    @NotNull
    Duration retryBackoff,

    @NotNull
    Set<HttpStatus> retryHttpStatuses
) {
}
