package com.vitu.spring.dynamic.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface PersonEnrichment<P, E> {

    boolean handle(PersonContext<P, E> personContext) throws JsonProcessingException;
}
