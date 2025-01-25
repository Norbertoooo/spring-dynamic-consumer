package com.vitu.spring.dynamic.consumer.scheduller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.vitu.spring.dynamic.consumer.consumer.PersonConsumer.CONSUMER_ONE_ID;
import static com.vitu.spring.dynamic.consumer.consumer.PersonConsumer.CONSUMER_TWO_ID;

@Component
public class PersonScheduler {

    private static final Logger log = LoggerFactory.getLogger(PersonScheduler.class);

    private final List<String> containerIds = List.of(CONSUMER_ONE_ID, CONSUMER_TWO_ID);

    private final KafkaConsumerScheduler kafkaConsumerScheduler;

    public PersonScheduler(KafkaConsumerScheduler kafkaConsumerScheduler) {
        this.kafkaConsumerScheduler = kafkaConsumerScheduler;
    }

    @Scheduled(cron = "${app.scheduler.person.start}")
    public void startConsume() {
        log.info("starting scheduler");
        containerIds.forEach(kafkaConsumerScheduler::startConsumer);
    }

    @Scheduled(cron = "${app.scheduler.person.stop}")
    public void stopConsume() {
        log.info("stopping scheduler");
        containerIds.forEach(kafkaConsumerScheduler::stopConsumer);
    }

}
