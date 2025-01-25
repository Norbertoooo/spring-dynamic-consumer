package com.vitu.spring.dynamic.consumer.repository;

import com.vitu.spring.dynamic.consumer.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
