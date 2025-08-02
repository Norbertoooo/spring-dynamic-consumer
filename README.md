# Spring Dynamic Consumer

A Spring Boot application that demonstrates dynamic Kafka consumer patterns with multiple enrichment steps for processing person data. This project showcases a flexible architecture for consuming messages from Kafka, enriching them with data from various services, and storing the results.

## Architecture Overview

The application follows a microservices architecture with the following components:

- **Kafka Consumer**: Dynamically processes person data from Kafka topics
- **Enrichment Chain**: Multiple enrichment steps that add additional data to person records
- **External Service Integration**: Connects to various services (loans, credit cards, investments) to enrich data
- **Data Persistence**: Stores enriched data in a database
- **Airflow Integration**: Uses Airflow for orchestrating data pipelines and scheduling tasks

### System Architecture Diagram

```
┌─────────────┐     ┌─────────────┐     ┌─────────────────────┐
│             │     │             │     │                     │
│  Database   │────▶│   Airflow   │────▶│    Kafka Topics     │
│  (Oracle)   │     │    DAGs     │     │                     │
│             │     │             │     │                     │
└─────────────┘     └─────────────┘     └──────────┬──────────┘
                                                   │
                                                   ▼
┌────────────────────────────────────────────────────────────┐
│                                                            │
│                 Spring Dynamic Consumer                    │
│                                                            │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │             │    │             │    │             │     │
│  │   Kafka     │───▶│ Enrichment  │───▶│    Data     │     │
│  │  Consumer   │    │    Chain    │    │ Persistence │     │
│  │             │    │             │    │             │     │
│  └─────────────┘    └──────┬──────┘    └─────────────┘     │
│                            │                               │
└────────────────────────────┼───────────────────────────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │                 │
                    │ External APIs   │
                    │ (Loan, Credit,  │
                    │  Investment)    │
                    │                 │
                    └─────────────────┘
```

### Key Components

- **PersonConsumer**: Kafka listener that processes person messages
- **PersonEnrichment**: Interface defining the enrichment contract
- **Enrichment Implementations**: Various implementations (LoanEnrichment, CreditCardEnrichment, InvestmentEnrichment)
- **PersonProducer**: Produces test messages to Kafka topics
- **Airflow DAGs**: Orchestrates data pipelines and database-to-Kafka flows

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- Kafka
- Oracle Database
- Airflow

## Installation and Setup

### Using Docker Compose

The easiest way to run the application is using Docker Compose:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-dynamic-consumer.git
   cd spring-dynamic-consumer
   ```

2. Start the infrastructure services:
   ```bash
   cd docker
   docker-compose -f project-compose.yml up -d
   ```

3. Build and run the application:
   ```bash
   ./mvnw clean package
   java -jar target/spring-dynamic-consumer.jar
   ```

### Manual Setup

1. Start Kafka, Zookeeper, and Oracle DB
2. Configure application properties
3. Build and run the application

## Usage Examples

### Producing Messages to Kafka

The application includes a scheduled producer that sends person data to Kafka every 10 seconds:

```java
@Scheduled(fixedDelay = 10000)
public void send() {
    log.info("PersonProducer is running");
    kafkaTemplate.send(topic, "key", Person.builder().id(1L).document("12332112332").name("draven").build());
}
```

### Consuming and Enriching Messages

The PersonConsumer listens to Kafka topics and processes messages through a chain of enrichment steps:

```java
@KafkaListener(id = CONSUMER_ONE_ID, topics = "${app.kafka.person.topic}", autoStartup = "false", batch = "true")
public void consumerOne(List<ConsumerRecord<String, Person>> records, Acknowledgment ack) {
    records.forEach(record -> {
        var personEnrichmentDto = PersonEnrichmentDto.builder()
                .id(record.value().getId())
                .name(record.value().getName())
                .document(record.value().getDocument())
                .build();

        for (var enrichment : personEnrichments) {
            boolean stop = enrichment.handle(new PersonContext<>(record.value(), personEnrichmentDto));
            if (stop) break;
        }

        personEnrichmentRepository.save(personEnrichmentDto);
    });
    
    ack.acknowledge();
}
```

### Using Airflow DAGs

The project includes Airflow DAGs for orchestrating data pipelines:

```
# Example from person_dag.py
import datetime
import json

from airflow import DAG
from airflow.providers.common.sql.operators.sql import SQLExecuteQueryOperator
from airflow.providers.apache.kafka.operators.produce import ProduceToTopicOperator

# Define connection ID
oracle_conn_id = 'oracle_conn'

# Define producer function
def producer(data):
    key = b'person-key'
    
    for item in data:
        if not isinstance(item, (list, tuple)) or len(item) < 4:
            continue
            
        id_, nome, cpf, data_ = item
        
        record = {
            "id": id_,
            "nome": nome,
            "cpf": cpf,
            "data": data_,
        }
        
        value = json.dumps(record).encode('utf-8')
        yield key, value

# Create DAG
person_dag = DAG(
    dag_id="person_dag",
    start_date=datetime.datetime(2021, 1, 1),
    schedule=None,
)

# SQL task to extract data
primeira_task = SQLExecuteQueryOperator(
    dag=person_dag,
    task_id="primeira_task", 
    do_xcom_push=True, 
    sql="SELECT * FROM airflow.person",
    conn_id=oracle_conn_id,
    show_return_value_in_logs='True'
)

# Kafka producer task
segunda_task = ProduceToTopicOperator(
    kafka_config_id="kafka_conn",
    task_id="segunda_task",
    topic="person.topic",
    producer_function=producer, 
    producer_function_kwargs={"data": "{{ti.xcom_pull(task_ids='primeira_task', key='return_value')}}"}
)

# Define task dependencies
primeira_task >> segunda_task
```

## Configuration Options

### Application Properties

Key configuration options in `application.properties`:

```properties
spring.application.name=spring-dynamic-consumer
spring.config.import=configserver:http://localhost:8888
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.producer.key-serializer=org.springframework.kafka.support.serializer.StringOrBytesSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

Additional configuration options can be found in `application-local.properties`.

## Development Guidelines

### Adding New Enrichment Steps

1. Create a new class that implements the `PersonEnrichment` interface
2. Add the `@Component` and `@Order` annotations
3. Implement the `handle` method to perform the enrichment
4. Return `false` to continue the chain or `true` to stop processing

Example:
```java
@Component
@Order(3)
public class NewEnrichment implements PersonEnrichment<Person, PersonEnrichmentDto> {
    @Override
    public boolean handle(PersonContext<Person, PersonEnrichmentDto> personContext) {
        // Enrichment logic here
        return false; // Continue the chain
    }
}
```

### Testing

The project includes unit tests for components. Run tests with:

```bash
./mvnw test
```

## Deployment

### Development Environment

Use the provided Docker Compose files to set up the development environment:

```bash
docker-compose -f docker/project-compose.yml up -d
```

### Production Environment

For production deployment:

1. Configure environment-specific properties
2. Use Kubernetes or similar orchestration platform
3. Set up monitoring with Prometheus and Grafana

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.