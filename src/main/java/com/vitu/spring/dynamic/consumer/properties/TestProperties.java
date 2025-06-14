package com.vitu.spring.dynamic.consumer.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.test")
// todo por algum motivo, o uso de record não funcionou para realizar a atualizar do valor
public class TestProperties {

    private String name;
}
