package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessedMessagesCounterConfig {
    @Bean
    @Autowired
    public Counter processedMessagesCounter(MeterRegistry meterRegistry) {
        return meterRegistry.counter("processed_messages_total");
    }
}
