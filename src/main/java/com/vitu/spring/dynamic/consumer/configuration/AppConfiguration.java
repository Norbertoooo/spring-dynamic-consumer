package com.vitu.spring.dynamic.consumer.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableFeignClients(basePackages = "com.vitu.spring.dynamic.consumer.client")
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.vitu.spring.dynamic.consumer.properties")
public class AppConfiguration {
}
