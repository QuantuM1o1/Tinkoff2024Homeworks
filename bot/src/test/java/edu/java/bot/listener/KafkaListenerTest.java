package edu.java.bot.listener;

import bot.Updates;
import edu.java.bot.configuration.KafkaConsumerConfig;
import edu.java.bot.configuration.KafkaProducerConfig;
import edu.java.bot.serdes.UpdateSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static org.junit.Assert.assertTrue;

@Testcontainers
@SpringBootTest(classes = { UpdatesKafkaListener.class, KafkaConsumerConfig.class, KafkaProducerConfig.class })
public class KafkaListenerTest {
    @Container
    private static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    static KafkaProducer<String, Updates.Update> testKafkaProducer;

    private static final String TOPIC = "scrapper.update";

    @Autowired
    private UpdatesKafkaListener updatesKafkaListener;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    @BeforeAll
    static void beforeAll() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UpdateSerializer.class.getName());
        testKafkaProducer = new KafkaProducer<>(props);
    }

    @Test
    @DisplayName("Тест получения сообщения")
    void receivingMessage()
        throws InterruptedException, ExecutionException, TimeoutException {
        // given
        Updates.Update update = Updates.Update.newBuilder()
            .setDescription("new update")
            .setId(12L)
            .setUrl("https://www.google.com/")
            .addTgChatIds(123L)
            .build();

        // when
        testKafkaProducer.send(new ProducerRecord<>(TOPIC, "test key", update));
        testKafkaProducer.flush();
        boolean answer = updatesKafkaListener.getLatch().await(10L, TimeUnit.SECONDS);

        // then
        assertTrue(answer);
    }
}
