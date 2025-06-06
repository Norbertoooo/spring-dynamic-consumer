package com.vitu.spring.dynamic.consumer.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.vitu.spring.dynamic.consumer.consumer.PersonConsumer.*;

@Component
public class PersonScheduler {

    private static final Logger log = LoggerFactory.getLogger(PersonScheduler.class);

    private final List<String> containerIds = List.of(CONSUMER_ONE_ID, CONSUMER_TWO_ID, CONSUMER_THREE_ID,
            CONSUMER_FOUR_ID, CONSUMER_FIVE_ID);

    private final KafkaConsumerScheduler kafkaConsumerScheduler;

    public PersonScheduler(KafkaConsumerScheduler kafkaConsumerScheduler) {
        this.kafkaConsumerScheduler = kafkaConsumerScheduler;
    }

    @Scheduled(cron = "${app.scheduler.person.start}")
    public void startConsume() {
        log.info("starting person scheduler");
        containerIds.forEach(kafkaConsumerScheduler::startConsumer);
    }

    @Scheduled(cron = "${app.scheduler.person.stop}")
    public void stopConsume() {
        log.info("stopping person scheduler");
        containerIds.forEach(kafkaConsumerScheduler::stopConsumer);
    }

}
