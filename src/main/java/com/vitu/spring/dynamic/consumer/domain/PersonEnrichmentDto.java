package com.vitu.spring.dynamic.consumer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEnrichmentDto {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long selfId;
    private Long id;
    private String name;
    private String document;

    @Column(columnDefinition = "TEXT")
    private String loanEnrichment;

    @Column(columnDefinition = "TEXT")
    private String cardCreditEnrichment;

    @Column(columnDefinition = "TEXT")
    private String investmentEnrichment;
}
