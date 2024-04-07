package edu.java.bot.configuration;

import bot.Updates;
import edu.java.bot.serdes.UpdateDeserializer;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Updates.Update> protobufKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Updates.Update> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.applicationConfig.kafkaUrl(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, UpdateDeserializer.class.getName()
        )));

        return factory;
    }
}
