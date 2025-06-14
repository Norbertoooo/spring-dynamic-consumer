package com.vitu.spring.dynamic.consumer.scheduler;

import com.vitu.spring.dynamic.consumer.properties.PersonProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Component
public class KafkaConsumerScheduler {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final PersonProperties personProperties;

    public KafkaConsumerScheduler(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
                                  PersonProperties personProperties) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.personProperties = personProperties;
    }

    public void startConsumer(String containerId) {
        this.consume(containerId, TRUE);
    }

    public void stopConsumer(String containerId) {
        this.consume(containerId, FALSE);
    }

    //TODO melhorar lógica de start e stop, talvez criar uma classe abstrata para evitar duplicação de código
    private void consume(String containerId, Boolean flag) {

        var listenerContainer = Optional.ofNullable(kafkaListenerEndpointRegistry.getListenerContainer(containerId));

        if (listenerContainer.isPresent()) {
            if (personProperties.getEnable()) {
                if (flag) {
                    log.info("starting container: {}", containerId);
                    if (listenerContainer.get().isRunning()) {
                        log.info("container {} is already running", containerId);
                    } else {
                        listenerContainer.get().start();
                    }
                } else {
                    log.info("destroying container: {}", containerId);
                    listenerContainer.get().destroy();
                }
            } else {
                log.info("scheduler is disabled, not starting or stopping container: {}", containerId);
                listenerContainer.get().destroy();
            }
        } else {
            log.warn("listener container not found: {}", containerId);
        }
    }
}
