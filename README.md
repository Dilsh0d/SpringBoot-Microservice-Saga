# SpringBoot Microservice Saga Orchestration Framework
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dilsh0d/spring-microservice-saga/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dilsh0d/spring-microservice-saga)

Saga Orchestration Framework writing for provide transactions in the Spring Boot Microserviceds. We know for transaction monolithic application working as ACID principe but, at the microservice architecture does not work it's concept. What is **ACID**? ACID is abviature, to use in Relational Database Management System for manage transactions.

- Atomic: results of a transaction are seen entirely or not at all within other transactions.
(A transaction need not appear atomic to itself.)
- Consistent: system-defined consistency constraints are enforced on the results of transactions. (Not going to discuss constraint checking today.)
- Isolated: transactions are not affected by the behavior of concurrently-running transactions.
- Durable: once a transaction commits, its results will not be lost regardless of subsequent failures.

Microservice architecture has each application it's databases. Our business logic everything does not work only one microservice and sometimes your a business logic must to work more than two microservices. That situation doesn't work ACID concept to whole business logic and in the database will be to full up with doesn't finished data. For this will use saga orchestration design pattern for  distributed transactions. In details about it click this is [link](https://www.infoq.com/articles/saga-orchestration-outbox/).

## Descriptions architecture
This is framework to conversation microservice used  **JMS** api

## Install.
1. [Apache kafka](https://kafka.apache.org/downloads)
2. [Redis](https://redis.io/download)
3. [Java 8 or later](https://www.oracle.com/java/technologies/javase-downloads.html)

## Getting started

1.You must add each microservice **pom.xml** its dependency:
```
<dependency>
  <groupId>io.github.dilsh0d</groupId>
  <artifactId>spring-microservice-saga</artifactId>
  <version>0.0.6</version>
</dependency>
```
2. Declaration @EnableSagaOrchestration microservice in the starter app class. This annotation auto configure **Kafka** and **Redis** client connection.
[example from project](https://github.com/Dilsh0d/SpringCloudMicroservice/blob/main/order-service/src/main/java/io/github/dilsh0d/OrderServiceApplication.java)
```java
  @EnableSagaOrchestration
  @EnableEurekaClient
  @SpringBootApplication
  public class OrderServiceApplication {

      public static void main(String[] args) {
          SpringApplication.run(OrderServiceApplication.class, args);
      }

  }
```

3. **application.properties** or **application.yml** file add this configuration
[example from project](https://github.com/Dilsh0d/SpringCloudMicroservice/blob/main/order-service/src/main/resources/application.yml)
```javascript
  kafka:
    producer:
      transaction-id-prefix: saga_pattern_event
      client-id: events_producer
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      bootstrap-servers: localhost:9092
    consumer:
      groupid: saga_pattern_order_event
      client-id: events_order_consumer
      auto-offset-reset: earliest
      bootstrap-servers: localhost:9092
      max-poll-records: 1
  redis:
    host: localhost
    port: 6379  
```

