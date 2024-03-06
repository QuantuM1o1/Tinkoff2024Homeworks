package edu.java.linkUpdater;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scheduler")
public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
}
