# spring-dynamic-consumer

## Objetivo


## Mapeamento das portas

| Nome                    | Porta | Porta na rasp |
|-------------------------|-------|---------------|
| spring-dynamic-consumer | 8081  |               |
| Jenkins                 | 8086  |               |
| Airflow                 | 8085  |               |
| Prometheus              | 9090  | 9090          |
| Grafana                 | 3000  | 3000          |
| Kafka-ui                | 8080  | 30080         |
| Kafka                   | 9092  | 30094         |
| Schema registry         | 8084  |               |
| Oracle                  | 1521  |               |
| config-server           | 8888  |               |

## Config-server

O `config-server` é um serviço responsável por fornecer configurações centralizadas para outros serviços. Ele permite armazenar propriedades, arquivos de configuração e outros recursos necessários para a configuração dos aplicativos, além de possibilitar a atualização dinâmica desses recursos sem a necessidade de reiniciar os serviços consumidores.

Mais informações: [spring-cloud-config-server](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)

As classes responsáveis por essa atualização dinâmica são:
- [`PersonProperties.java`](src/main/java/com/vitu/spring/dynamic/consumer/properties/PersonProperties.java): realiza o mapeamento das propriedades.
- [`EnvironmentRefreshSchedule.java`](src/main/java/com/vitu/spring/dynamic/consumer/configuration/EnvironmentRefreshSchedule.java): executa o refresh das propriedades em um intervalo definido pela propriedade `app.scheduler.refresh.cron`.

### Repositórios
[config-server](https://github.com/Norbertoooo/config-server)

[config-server-properties](https://github.com/Norbertoooo/config-server-properties)

## Ferramentas

- [x] Spring boot
- [x] kafka
- [x] grafana
- [x] prometheus
- [x] jenkins
- [x] k8s
- [ ] cucumber
- [ ] robot
- [x] airflow
- [ ] avro
- [ ] schema registry
- [ ] mongodb
- [x] oracle
- [ ] checkstyle
- [x] jacoco
- [ ] sonar
- [x] config server
- [ ] flyway