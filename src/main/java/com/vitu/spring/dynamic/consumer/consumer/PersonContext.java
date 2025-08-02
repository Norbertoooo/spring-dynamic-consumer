package com.vitu.spring.dynamic.consumer.consumer;

import com.vitu.spring.dynamic.consumer.domain.Person;
import com.vitu.spring.dynamic.consumer.domain.PersonEnrichmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonContext<P, E> {

    private Person person;
    private PersonEnrichmentDto personEnrichmentDto;
}
