package com.vitu.spring.dynamic.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitu.spring.dynamic.consumer.client.LoanClient;
import com.vitu.spring.dynamic.consumer.domain.Person;
import com.vitu.spring.dynamic.consumer.domain.PersonEnrichmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class LoanEnrichment implements PersonEnrichment<Person, PersonEnrichmentDto> {

    private final LoanClient loanClient;
    private final ObjectMapper objectMapper;

    @Override
    public boolean handle(PersonContext<Person,PersonEnrichmentDto> personContext) throws JsonProcessingException {
        log.info("LoanEnrichment is running");
        Person person = personContext.getPerson();
        PersonEnrichmentDto personEnrichmentDto = personContext.getPersonEnrichmentDto();
        ResponseEntity<?> loansByDocument = loanClient.getLoansByDocument(person.getDocument());
        String valueAsString = objectMapper.writeValueAsString(loansByDocument.getBody());
        personEnrichmentDto.setLoanEnrichment(valueAsString);
        return false;
    }
}
