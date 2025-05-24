package com.vitu.spring.dynamic.consumer.scheduller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.vitu.spring.dynamic.consumer.consumer.PersonConsumer.*;
import static org.mockito.Mockito.*;

class PersonSchedulerTest {

    private KafkaConsumerScheduler kafkaConsumerScheduler;
    private PersonScheduler personScheduler;

    @BeforeEach
    void setUp() {
        kafkaConsumerScheduler = mock(KafkaConsumerScheduler.class);
        personScheduler = new PersonScheduler(kafkaConsumerScheduler);
    }

    @Test
    void testStartConsume() {
        // Act
        personScheduler.startConsume();

        // Assert
        List.of(CONSUMER_ONE_ID, CONSUMER_TWO_ID, CONSUMER_THREE_ID, CONSUMER_FOUR_ID, CONSUMER_FIVE_ID)
                .forEach(containerId -> verify(kafkaConsumerScheduler, times(1)).startConsumer(containerId));
    }

    @Test
    void testStopConsume() {
        // Act
        personScheduler.stopConsume();

        // Assert
        List.of(CONSUMER_ONE_ID, CONSUMER_TWO_ID, CONSUMER_THREE_ID, CONSUMER_FOUR_ID, CONSUMER_FIVE_ID)
                .forEach(containerId -> verify(kafkaConsumerScheduler, times(1)).stopConsumer(containerId));
    }
}
