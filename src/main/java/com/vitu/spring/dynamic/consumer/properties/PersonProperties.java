package com.vitu.spring.dynamic.consumer.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.scheduler.person")
public class PersonProperties {

    private Boolean enable;

}
