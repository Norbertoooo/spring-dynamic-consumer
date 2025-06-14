package com.vitu.spring.dynamic.consumer.scheduler;

import com.vitu.spring.dynamic.consumer.properties.TestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestScheduler {

    private final TestProperties testProperties;

    public TestScheduler(TestProperties testProperties) {
        this.testProperties = testProperties;
    }

    @Scheduled(fixedDelay = 10000)
    public void test() {
        log.info("TestScheduler is running");
        log.info("TestProperties name: {}", testProperties.getName());
    }
}
