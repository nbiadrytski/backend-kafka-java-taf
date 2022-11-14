package io.fraud.kafka;

import io.fraud.kafka.consumer.KafkaMessageConsumer;
import io.fraud.kafka.producer.KafkaMessageProducer;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaService {

    private final KafkaMessageProducer kafkaMessageProducer;
    private final KafkaMessageConsumer messageConsumer;

    public KafkaService(String server) {
        this.kafkaMessageProducer = new KafkaMessageProducer(server);  // localhost:9092
        this.messageConsumer = new KafkaMessageConsumer(server);
    }

    public KafkaMessageConsumer consumer() {
        return messageConsumer;
    }

//    public DealMessage sendMessage() {
//        String message = testDataGenerator.generate("data/message.twig");
//        RecordMetadata send = kafkaMessageProducer.send("queuing.transactions", message);
//        return readValue(message, DealMessage.class);
//    }

    public RecordMetadata send(String message) {
        return kafkaMessageProducer.send("test", message);
    }

    public RecordMetadata send(String topic, String message) {
        return kafkaMessageProducer.send(topic, message);
    }

    public void subscribe(String topic) {
        messageConsumer.subscribe(topic);
        messageConsumer.consume();
    }

    public KafkaRecord waitForMessage(String message) {
        return messageConsumer.waitForMessage(message);
    }
}
