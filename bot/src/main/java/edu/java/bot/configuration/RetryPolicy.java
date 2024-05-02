package edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;

public record RetryPolicy(
    @NotNull
    RetryType type,

    @NotNull
    int attempts,

    @NotNull
    Duration backoff,

    @NotNull
    Set<Integer> httpStatusCodes
) {
}
