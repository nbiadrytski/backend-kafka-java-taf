package io.fraud.kafka.producer;

import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageProducer {

    private static KafkaProducer<String, String> kafkaProducer;
    private final String bootStrapServer;  // localhost:9090, this is kafka address

    public KafkaMessageProducer(String bootStrapServer) {
        this.bootStrapServer = bootStrapServer;
        kafkaProducer = new KafkaProducer<>(createProducerProperties());
    }

    public KafkaMessageProducer createProducer() {
        Properties properties = createProducerProperties();
        if (kafkaProducer == null) {
            kafkaProducer = new KafkaProducer<>(properties);
        }
        return this;
    }

    private Properties createProducerProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootStrapServer);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @SneakyThrows  // replaces try catch block for kafkaProducer.send(record).get();
    public RecordMetadata send(String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        return kafkaProducer.send(record).get();
    }
}
