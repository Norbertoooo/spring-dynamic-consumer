package com.vitu.spring.dynamic.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PersonConsumer {

    public static final String CONSUMER_ONE_ID = "CONSUMER_ONE_ID";
    public static final String CONSUMER_TWO_ID = "CONSUMER_TWO_ID";
    public static final String CONSUMER_THREE_ID = "CONSUMER_THREE_ID";
    public static final String CONSUMER_FOUR_ID = "CONSUMER_FOUR_ID";
    public static final String CONSUMER_FIVE_ID = "CONSUMER_FIVE_ID";

    @KafkaListener(id = CONSUMER_ONE_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
    public void consumerOne(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        log.info("consumer one {}", records);
    }

    @KafkaListener(id = CONSUMER_TWO_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
    public void consumerTwo(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        log.info("consumer two: {}", records);
    }

}
