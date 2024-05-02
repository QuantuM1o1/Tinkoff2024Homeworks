package edu.java.configuration;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import edu.java.serdes.UpdateDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import scrapper.Updates;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EmbeddedKafka(partitions = 1)
@SpringBootTest(classes = KafkaProducerConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaProducerTest {
    private final String TOPIC_NAME = "scrapper.update";

    private BlockingQueue<ConsumerRecord<String, Updates.Update>> records;

    private KafkaMessageListenerContainer<String, Updates.Update> container;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaTemplate<String, Updates.Update> producer;

    @BeforeAll
    void setUp() {
        DefaultKafkaConsumerFactory<String, Updates.Update> consumerFactory
            = new DefaultKafkaConsumerFactory<>(getConsumerProperties());
        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, Updates.Update>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, this.embeddedKafkaBroker.getPartitionsPerTopic());
    }

    private Map<String, Object> getConsumerProperties() {
        return Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.embeddedKafkaBroker.getBrokersAsString(),
            ConsumerConfig.GROUP_ID_CONFIG, "consumer",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true",
            ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10",
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UpdateDeserializer.class.getName(),
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

    @Test
    @DisplayName("Отправка сообщения в Кафку")
    void testWriteToKafka() throws InterruptedException {
        // given
        Updates.Update update = Updates.Update.newBuilder()
            .setDescription("new update")
            .setId(12L)
            .setUrl("https://www.google.com/")
            .addTgChatIds(123L)
            .build();

        // when
        this.producer.send(new ProducerRecord<>(TOPIC_NAME, "testKey", update));
        ConsumerRecord<String, Updates.Update> answer = records.poll(500, TimeUnit.MILLISECONDS);

        // then
        assertThat(answer).isNotNull();
        assertThat(answer.key()).isEqualTo("testKey");
        assertThat(answer.value().getUrl()).isEqualTo("https://www.google.com/");
    }
}
