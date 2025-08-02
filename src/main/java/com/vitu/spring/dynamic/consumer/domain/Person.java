package com.vitu.spring.dynamic.consumer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private Long id;
    private String name;
    private String document;
}
