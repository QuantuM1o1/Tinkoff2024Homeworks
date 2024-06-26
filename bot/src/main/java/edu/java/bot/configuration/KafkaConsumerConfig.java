package edu.java.bot.configuration;

import bot.Updates;
import edu.java.bot.serdes.UpdateDeserializer;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Autowired
    private KafkaTemplate<String, Updates.Update> updateKafkaTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Updates.Update> protobufKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Updates.Update> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapAddress,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, UpdateDeserializer.class.getName()
        )));

        DeadLetterPublishingRecoverer recover = new DeadLetterPublishingRecoverer(updateKafkaTemplate,
            (r, e) -> new TopicPartition(r.topic() + ".dlq", r.partition())
        );
        CommonErrorHandler errorHandler = new DefaultErrorHandler(recover);
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}
