package com.vitu.spring.dynamic.consumer.producer;

import com.vitu.spring.dynamic.consumer.domain.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonProducer {

    private final KafkaTemplate<String, Person> kafkaTemplate;

    @Value("${app.kafka.person.topic}")
    private String topic;

    @Scheduled(fixedDelay = 10000)
    public void send() {
        log.info("PersonProducer is running");
        kafkaTemplate.send(topic, "key", Person.builder().id(1L).document("12332112332").name("draven").build());
    }
}
