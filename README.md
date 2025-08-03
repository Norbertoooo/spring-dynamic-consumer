# Spring Dynamic Consumer

Uma aplicação Spring Boot que demonstra padrões dinâmicos de consumo do Kafka com múltiplas etapas de enriquecimento para processamento de dados de pessoas. Este projeto apresenta uma arquitetura flexível para consumir mensagens do Kafka, enriquecê-las com dados de vários serviços e armazenar os resultados.

## Visão Geral da Arquitetura

A aplicação segue uma arquitetura de microsserviços com os seguintes componentes:

- **Consumidor Kafka**: Processa dinamicamente dados de pessoas a partir de tópicos do Kafka
- **Cadeia de Enriquecimento**: Múltiplas etapas de enriquecimento que adicionam dados adicionais aos registros de pessoas
- **Integração com Serviços Externos**: Conecta-se a vários serviços (empréstimos, cartões de crédito, investimentos) para enriquecer dados
- **Persistência de Dados**: Armazena dados enriquecidos em um banco de dados
- **Integração com Airflow**: Utiliza o Airflow para orquestrar pipelines de dados e agendar tarefas

## Padrões de Projeto Utilizados

O projeto implementa diversos padrões de design para garantir flexibilidade, manutenibilidade e extensibilidade:

### Padrões Comportamentais

1. **Chain of Responsibility**: 
   - Implementado na cadeia de enriquecimento onde cada handler (LoanEnrichment, CreditCardEnrichment, InvestmentEnrichment) processa os dados sequencialmente
   - Cada implementação de `PersonEnrichment` decide se continua a cadeia (retornando `false`) ou interrompe o processamento (retornando `true`)
   - A ordem de execução é controlada pela anotação `@Order` em cada implementação

2. **Strategy**: 
   - Diferentes estratégias de enriquecimento implementam a mesma interface `PersonEnrichment`
   - Permite adicionar novas estratégias de enriquecimento sem modificar o código existente
   - Facilita a troca de algoritmos de enriquecimento em tempo de execução

3. **Observer**:
   - Implementado através do sistema de eventos do Kafka
   - Os consumidores observam tópicos específicos e reagem quando novas mensagens são publicadas

### Padrões Criacionais

1. **Builder**:
   - Utilizado para criar objetos complexos como `Person` e `PersonEnrichmentDto`
   - Permite a construção de objetos passo a passo com sintaxe fluente

### Padrões Estruturais

1. **Adapter**:
   - Usado para integrar serviços externos através de clients como `LoanClient`
   - Converte as respostas externas em formatos compatíveis com o modelo interno

2. **Composite**:
   - A lista de enriquecimentos (`personEnrichments`) é tratada como uma composição de objetos individuais
   - Permite tratar um grupo de objetos da mesma forma que um objeto único

### Padrões Arquiteturais

1. **Dependency Injection**:
   - Implementado através do Spring Framework
   - Componentes recebem suas dependências em vez de criá-las
   - Facilita testes e desacoplamento

2. **Repository**:
   - Abstrai o acesso a dados através de interfaces como `PersonEnrichmentRepository`
   - Encapsula a lógica de armazenamento e recuperação de dados

3. **Producer-Consumer**:
   - Implementado com Kafka para comunicação assíncrona
   - Desacopla a produção e o consumo de mensagens

### Diagrama da Arquitetura do Sistema

```
┌─────────────┐     ┌─────────────┐     ┌─────────────────────┐
│             │     │             │     │                     │
│  Banco de   │────▶│   Airflow   │────▶│  Tópicos do Kafka   │
│   Dados     │     │    DAGs     │     │                     │
│  (Oracle)   │     │             │     │                     │
└─────────────┘     └─────────────┘     └──────────┬──────────┘
                                                   │
                                                   ▼
┌────────────────────────────────────────────────────────────┐
│                                                            │
│                 Spring Dynamic Consumer                    │
│                                                            │
│  ┌─────────────┐    ┌──────────────┐    ┌─────────────┐    │
│  │             │    │              │    │             │    │
│  │ Consumidor  │───▶│ Cadeia de    │───▶│ Persistência│    │
│  │   Kafka     │    │Enriquecimento│    │  de Dados   │    │
│  │             │    │              │    │             │    │
│  └─────────────┘    └──────┬───────┘    └─────────────┘    │
│                            │                               │
└────────────────────────────┼───────────────────────────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │                 │
                    │   APIs Externas │
                    │ (Empréstimo,    │
                    │ Crédito,        │
                    │ Investimento)   │
                    │                 │
                    └─────────────────┘
```

### Componentes Principais

- **PersonConsumer**: Listener do Kafka que processa mensagens de pessoas
- **PersonEnrichment**: Interface que define o contrato de enriquecimento
- **Implementações de Enriquecimento**: Várias implementações (LoanEnrichment, CreditCardEnrichment, InvestmentEnrichment)
- **PersonProducer**: Produz mensagens de teste para tópicos do Kafka
- **DAGs do Airflow**: Orquestra pipelines de dados e fluxos de banco de dados para Kafka

## Pré-requisitos

- Java 21
- Maven 3.6+
- Docker e Docker Compose
- Kafka
- Banco de Dados Oracle
- Airflow

## Instalação e Configuração

### Usando Docker Compose

A maneira mais fácil de executar a aplicação é usando o Docker Compose:

1. Clone o repositório:
   ```bash
   git clone https://github.com/yourusername/spring-dynamic-consumer.git
   cd spring-dynamic-consumer
   ```

2. Inicie os serviços de infraestrutura:
   ```bash
   cd docker
   docker-compose -f project-compose.yml up -d
   ```

3. Compile e execute a aplicação:
   ```bash
   ./mvnw clean package
   java -jar target/spring-dynamic-consumer.jar
   ```

### Configuração Manual

1. Inicie o Kafka, Zookeeper e Oracle DB
2. Configure as propriedades da aplicação
3. Compile e execute a aplicação

## Exemplos de Uso

### Produzindo Mensagens para o Kafka

A aplicação inclui um produtor agendado que envia dados de pessoas para o Kafka a cada 10 segundos:

```java
@Scheduled(fixedDelay = 10000)
public void send() {
    log.info("PersonProducer is running");
    kafkaTemplate.send(topic, "key", Person.builder().id(1L).document("12332112332").name("draven").build());
}
```

### Consumindo e Enriquecendo Mensagens

O PersonConsumer escuta tópicos do Kafka e processa mensagens através de uma cadeia de etapas de enriquecimento:

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

### Usando DAGs do Airflow

O projeto inclui DAGs do Airflow para orquestrar pipelines de dados:

```
# Exemplo de person_dag.py
import datetime
import json

from airflow import DAG
from airflow.providers.common.sql.operators.sql import SQLExecuteQueryOperator
from airflow.providers.apache.kafka.operators.produce import ProduceToTopicOperator

# Define o ID de conexão
oracle_conn_id = 'oracle_conn'

# Define a função do produtor
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

# Cria o DAG
person_dag = DAG(
    dag_id="person_dag",
    start_date=datetime.datetime(2021, 1, 1),
    schedule=None,
)

# Tarefa SQL para extrair dados
primeira_task = SQLExecuteQueryOperator(
    dag=person_dag,
    task_id="primeira_task", 
    do_xcom_push=True, 
    sql="SELECT * FROM airflow.person",
    conn_id=oracle_conn_id,
    show_return_value_in_logs='True'
)

# Tarefa do produtor Kafka
segunda_task = ProduceToTopicOperator(
    kafka_config_id="kafka_conn",
    task_id="segunda_task",
    topic="person.topic",
    producer_function=producer, 
    producer_function_kwargs={"data": "{{ti.xcom_pull(task_ids='primeira_task', key='return_value')}}"}
)

# Define as dependências de tarefas
primeira_task >> segunda_task
```

## Opções de Configuração

### Propriedades da Aplicação

Opções de configuração principais em `application.properties`:

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

Opções de configuração adicionais podem ser encontradas em `application-local.properties`.

## Diretrizes de Desenvolvimento

### Adicionando Novas Etapas de Enriquecimento

1. Crie uma nova classe que implementa a interface `PersonEnrichment`
2. Adicione as anotações `@Component` e `@Order`
3. Implemente o método `handle` para realizar o enriquecimento
4. Retorne `false` para continuar a cadeia ou `true` para interromper o processamento

Exemplo:
```java
@Component
@Order(3)
public class NewEnrichment implements PersonEnrichment<Person, PersonEnrichmentDto> {
    @Override
    public boolean handle(PersonContext<Person, PersonEnrichmentDto> personContext) {
        // Lógica de enriquecimento aqui
        return false; // Continua a cadeia
    }
}
```

### Testes

O projeto inclui testes unitários para componentes. Execute os testes com:

```bash
./mvnw test
```

## Implantação

### Ambiente de Desenvolvimento

Use os arquivos Docker Compose fornecidos para configurar o ambiente de desenvolvimento:

```bash
docker-compose -f docker/project-compose.yml up -d
```

### Ambiente de Produção

Para implantação em produção:

1. Configure propriedades específicas do ambiente
2. Use Kubernetes ou plataforma de orquestração similar
3. Configure monitoramento com Prometheus e Grafana

## Contribuindo

1. Faça um fork do repositório
2. Crie uma branch de feature
3. Faça suas alterações
4. Envie um pull request

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para detalhes.