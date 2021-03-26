# SpringBoot Microservice Saga Orchestration Framework
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dilsh0d/spring-microservice-saga/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dilsh0d/spring-microservice-saga)

Saga Orchestration Framework writing for provide transactions in the Spring Boot Microserviceds. We know for transaction monolithic application working as ACID principe but, at the microservice architecture does not work it's concept. What is **ACID**? ACID is abviature, to use in Relational Database Management System for manage transactions.

- Atomic: results of a transaction are seen entirely or not at all within other transactions.
(A transaction need not appear atomic to itself.)
- Consistent: system-defined consistency constraints are enforced on the results of transactions. (Not going to discuss constraint checking today.)
- Isolated: transactions are not affected by the behavior of concurrently-running transactions.
- Durable: once a transaction commits, its results will not be lost regardless of subsequent failures.

Microservice architecture has each application it's databases. Our business logic everything does not work only one microservice and sometimes your a business logic must to work more than two microservices. That situation doesn't work ACID concept to whole business logic and in the database will be to full up with doesn't finished data. For this will use saga orchestration design pattern for  distributed transactions. In details about it click this is [link](https://www.infoq.com/articles/saga-orchestration-outbox/).
