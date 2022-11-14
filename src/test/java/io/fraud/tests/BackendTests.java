package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


public class BackendTests {

    @Test
    void testCanWriteMessageToQueuingTransaction() {
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer("localhost:9092");
        KafkaMessageConsumer kafkaMessageConsumer = new KafkaMessageConsumer("localhost:9092");
        kafkaMessageConsumer.subscribe("test");
        kafkaMessageConsumer.consume();
        kafkaMessageProducer.send("test", "hello from Mikalai");

        KafkaRecord receivedRecords = kafkaMessageConsumer.waitForMessage("hello from Mikalai");

        assertThat(receivedRecords).isNotNull();
    }
}
