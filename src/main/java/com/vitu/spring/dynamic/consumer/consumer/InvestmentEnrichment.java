package com.vitu.spring.dynamic.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitu.spring.dynamic.consumer.client.InvestmentClient;
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
@Order(3)
@RequiredArgsConstructor
public class InvestmentEnrichment implements PersonEnrichment<Person, PersonEnrichmentDto> {

    private final InvestmentClient investmentClient;
    private final ObjectMapper objectMapper;

    @Override
    public boolean handle(PersonContext<Person, PersonEnrichmentDto> personContext) throws JsonProcessingException {
        log.info("InvestmentEnrichment is running");
        Person person = personContext.getPerson();
        PersonEnrichmentDto personEnrichmentDto = personContext.getPersonEnrichmentDto();

        ResponseEntity<?> loansByDocument = investmentClient.getInvestmentsByDocument(person.getDocument());

        String valueAsString = objectMapper.writeValueAsString(loansByDocument.getBody());

        personEnrichmentDto.setInvestmentEnrichment(Objects.requireNonNull(valueAsString));

        return false;
    }
}
