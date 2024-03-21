package edu.java.configuration;

import edu.java.linkUpdater.Scheduler;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Scheduler.class)
public class SchedulerConfig {
    @Autowired
    Scheduler scheduler;

    @NotNull
    @Bean
    public Scheduler scheduler() {
        return this.scheduler;
    }
}
