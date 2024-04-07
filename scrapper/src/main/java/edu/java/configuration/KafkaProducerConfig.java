package edu.java.configuration;

import edu.java.serdes.UpdateSerializer;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import scrapper.Updates;

@Configuration
public class KafkaProducerConfig {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public KafkaTemplate<String, Updates.Update> protobufKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.applicationConfig.kafkaUrl(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UpdateSerializer.class
        )));
    }
}
