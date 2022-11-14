package io.fraud.tests;

import io.fraud.kafka.KafkaRecord;
import io.fraud.kafka.KafkaService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


public class BackendTests {

    private final KafkaService kafkaService = new KafkaService("localhost:9092");

    @Test
    void testCanWriteMessageToQueuingTransaction() {
        kafkaService.subscribe("test");
        kafkaService.send("test", "hello from Mikalai");
        KafkaRecord receivedRecords = kafkaService.waitForMessage("hello from Mikalai");

        assertThat(receivedRecords).isNotNull();
    }

    @Test
    void testApplicationCanProcessValidMessage() {
        // Detector reads from queuing.transactions and writes to streaming.transactions.legit if message is valid
        kafkaService.subscribe("streaming.transactions.legit");
        // Generator writes to queuing.transactions
        kafkaService.send(
                "queuing.transactions",
                "{\"date\": \"07/14/2020 18:11:32\", \"source\": \"java12\", \"target\": \"python\", \"amount\": \"900.0\", \"currency\": \"EUR\"}"
        );

        KafkaRecord receivedRecords = kafkaService.waitForMessage("java12");
        assertThat(receivedRecords).isNotNull();
    }
}
