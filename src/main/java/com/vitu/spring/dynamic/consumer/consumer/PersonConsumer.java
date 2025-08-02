package com.vitu.spring.dynamic.consumer.consumer;

import com.vitu.spring.dynamic.consumer.domain.Person;
import com.vitu.spring.dynamic.consumer.domain.PersonEnrichmentDto;
import com.vitu.spring.dynamic.consumer.repository.PersonEnrichmentRepository;
import com.vitu.spring.dynamic.consumer.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonConsumer {

    public static final String CONSUMER_ONE_ID = "CONSUMER_ONE_ID";
    public static final String CONSUMER_TWO_ID = "CONSUMER_TWO_ID";
    public static final String CONSUMER_THREE_ID = "CONSUMER_THREE_ID";
    public static final String CONSUMER_FOUR_ID = "CONSUMER_FOUR_ID";
    public static final String CONSUMER_FIVE_ID = "CONSUMER_FIVE_ID";

    private final List<PersonEnrichment<Person, PersonEnrichmentDto>> personEnrichments;
    private final PersonService personService;
    private final PersonEnrichmentRepository personEnrichmentRepository;

    @KafkaListener(id = CONSUMER_ONE_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
    public void consumerOne(List<ConsumerRecord<String, Person>> records, Acknowledgment ack) {
        log.info("consumer one {}", records);
//
//        records.forEach(record -> {
//            log.info("Processing record: key={}, value={}", record.key(), record.value());
//            // Encadeamento
//            for (int i = 0; i < personEnrichments.size() - 1; i++) {
//                personEnrichments.get(i).setNext(personEnrichments.get(i + 1));
//            }
//
//            this.chainStart = personEnrichments.getFirst();
//
//            this.chainStart.enrich( record.value(), new PersonEnrichmentDto()).;
//
//        });

        records.forEach(record -> {
                    log.info("Processing record: key={}, value={}", record.key(), record.value());
            var personEnrichmentDto = PersonEnrichmentDto.builder()
                    .id(record.value().getId())
                    .name(record.value().getName())
                    .document(record.value().getDocument())
                    .build();

            for (var enrichment : personEnrichments) {
                        boolean stop;
                        try {
                            stop = enrichment.handle(new PersonContext<>(record.value(), personEnrichmentDto));
                            if (stop) break;
                        } catch (Exception e) {
                            log.error("Error processing record: key={}, value={}", record.key(), record.value(), e);
                        }
                    }

                    log.info("Person Enrichment finished: {}", personEnrichmentDto );
                    personEnrichmentRepository.save(personEnrichmentDto);
                }
        );

        log.info("Consumer one processed records: {}", records.size());
        ack.acknowledge();
        log.info("Acknowledged records in consumer one");
    }

//    @KafkaListener(id = CONSUMER_TWO_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
//    public void consumerTwo(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//        log.info("consumer two: {}", records);
//        ack.acknowledge();
//    }
//
//    @KafkaListener(id = CONSUMER_THREE_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
//    public void consumerThree(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//        log.info("consumer three: {}", records);
//        ack.acknowledge();
//    }
//
//    @KafkaListener(id = CONSUMER_FOUR_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
//    public void consumerFour(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//        log.info("consumer four: {}", records);
//        ack.acknowledge();
//    }
//
//    @KafkaListener(id = CONSUMER_FIVE_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
//    public void consumerFive(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//        log.info("consumer five: {}", records);
//        ack.acknowledge();
//    }

}
