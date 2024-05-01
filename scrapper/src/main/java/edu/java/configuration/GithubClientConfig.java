package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "github-client", ignoreUnknownFields = false)
public record GithubClientConfig(
    @NotEmpty
    String baseUrl,

    RetryPolicy retryPolicy
) {
}
