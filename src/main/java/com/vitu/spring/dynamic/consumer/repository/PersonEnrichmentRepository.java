package com.vitu.spring.dynamic.consumer.repository;

import com.vitu.spring.dynamic.consumer.domain.PersonEnrichmentDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonEnrichmentRepository extends JpaRepository<PersonEnrichmentDto, Long> {
}
