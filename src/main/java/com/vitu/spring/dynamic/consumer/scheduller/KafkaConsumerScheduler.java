package com.vitu.spring.dynamic.consumer.scheduller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Component
public class KafkaConsumerScheduler {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public KafkaConsumerScheduler(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    }

    public void startConsumer(String containerId) {
        this.consume(containerId, TRUE);
    }

    public void stopConsumer(String containerId) {
        this.consume(containerId, FALSE);
    }

    private void consume(String containerId, Boolean flag) {
        var listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(containerId);
        if (Objects.nonNull(listenerContainer)) {
            if (flag) {
                log.info("iniciando container: {}", containerId);
                listenerContainer.start();
            } else {
                log.info("destruindo container: {}", containerId);
                listenerContainer.destroy();
            }
        } else {
            log.warn("listener container not found: {}", containerId);
        }
    }
}
