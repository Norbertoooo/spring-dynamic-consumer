package com.vitu.spring.dynamic.consumer.controler;

import com.vitu.spring.dynamic.consumer.domain.PersonEnrichmentDto;
import com.vitu.spring.dynamic.consumer.repository.PersonEnrichmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonEnrichmentControler {

    private final PersonEnrichmentRepository personEnrichmentRepository;

    @GetMapping("/person-enrichment")
    public ResponseEntity<List<PersonEnrichmentDto>> getPersonEnrichment() {
        return ResponseEntity.ok().body(personEnrichmentRepository.findAll()) ;
    }

}
