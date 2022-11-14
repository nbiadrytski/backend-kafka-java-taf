package io.fraud.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KafkaRecord {

    private final ConsumerRecord<String, String> record;

    public KafkaRecord(ConsumerRecord<String, String> record) {
        this.record = record;
    }

    public boolean hasSourceId(String message) {
        return record.value().contains(message);
    }
}
