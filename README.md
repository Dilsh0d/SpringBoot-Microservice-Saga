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
This is framework to conversation microservice used **Apache Kafka** api and each event state save in the **Redis** to finish saga.
![Saga Architecture](https://i.ibb.co/b5wYdTV/Saga-Architecture.png)

## Install.
1. [Apache kafka](https://kafka.apache.org/downloads)
2. [Redis](https://redis.io/download)
3. [Java 8 or later](https://www.oracle.com/java/technologies/javase-downloads.html)

## Introduction
1. [Getting started](#getting-started)
2. [Saga tools](#saga-tools)
3. [@SagaAssociateId](#sagaorchestration)
4. [@SagaOrchestration](#sagaorchestration)
5. [@SagaOrchestEventHandler](#sagaorchesteventhandler)
6. [@SagaOrchestStart](#sagaorcheststart)
7. [@SagaOrchestEnd](#sagaorchestend)
8. [@SagaOrchestException](#sagaorchestexception)
9. [SagaGateway](#sagagateway)

## Getting Started

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
That all we configured all things from three steps.

## Saga tools
Next step we look at the framework **6** annotation tools which of what does is its.

| Annotations | Descreption |
| ------ | ------ |
| ```@SagaAssociateId``` | Saga associate will save in redis or get from redis. |
| ```@SagaOrchestration``` | Declaration only above saga class. |
| ```@SagaOrchestEventHandler``` | Declaration only above event hanler methods in the saga class   |
| ```@SagaOrchestStart``` | Declaration with @SagaOrchestEventHandler event handler methods, that is meant saga class started with method. |
| ```@SagaOrchestEnd``` | Declaration with @SagaOrchestEventHandler event handler methods, that is meant saga class ended with method |
| ```@SagaOrchestException``` | Declaration in the saga class exception handlers |

#### @SagaAssociateId
Annotation declaration saga instances event class unique id field and after start saga from this id to store use this **id** saga instance to redis and next actions for use  as (associate Id). You can declaration other need fields but don't forget create get/set methods so as doesn't parse in the save to rides.
```java
  @Getter
  @Setter
  public class CreateOrderEvent {

      @SagaAssociateId
      private String id;

      private List<Integer> items;

  }
```
#### @SagaOrchestration
Declaration on the class where distributed transaction management between microservice and this is annotation create Spring bean with scope prototype. If saga class will work with **@SagaOrchestStart** event handler then create spring instance else get by associate id from redis this object and create instance from this. Each come event to **Saga** class **@SagaOrchestEventHandler** method save to redis by associateId. If Event handler method declaration with annotation **@SagaOrchestEnd** then Saga Instance to finished its work and remove from redis by associate id. Saga class is to declare **@SagaOrchestException** so as we can catch exceptions and run **rollback()** events.

1.  Saga class declaration primitive and reference types you must create **get/set** methods else will be json parse exceptions.
2.  Saga class declaration spring bean fields must before add keyword **transient** and bean does not need create get/set method. 
```java
  @SagaOrchestration
  public class OrderSaga {

      private String orderId;
      private String paymentId;
      private PaymentType paymentType;
      private BigDecimal amount = new BigDecimal("20.5");
      private List<Integer> itemsId;

      public String getOrderId() {
          return orderId;
      }

      public void setOrderId(String orderId) {
          this.orderId = orderId;
      }

      public String getPaymentId() {
          return paymentId;
      }

      public void setPaymentId(String paymentId) {
          this.paymentId = paymentId;
      }

      public PaymentType getPaymentType() {
          return paymentType;
      }

      public void setPaymentType(PaymentType paymentType) {
          this.paymentType = paymentType;
      }

      public BigDecimal getAmount() {
          return amount;
      }

      public void setAmount(BigDecimal amount) {
          this.amount = amount;
      }

      public List<Integer> getItemsId() {
          return itemsId;
      }

      public void setItemsId(List<Integer> itemsId) {
          this.itemsId = itemsId;
      }

      @Autowired
      private transient SagaGateway sagaGateway;

      @Autowired
      private transient OrderPushNotification orderPushNotification;

      @Autowired
      private transient OrderService orderService;

      @SagaOrchestStart
      @SagaOrchestEventHandler
      public void handler(CreateOrderEvent event){
          this.orderId = event.getId();
          this.itemsId = event.getItems();

          orderService.createOrder(event, amount);
          orderPushNotification.sentClientNotification(event.getId(), 
          "YOUR ORDER CREATED, PLEASE SELECT PAYMENT TYPE AND PAY FROM IT : [ " + PaymentType.getStrings()+" ]");
      }

      @SagaOrchestEventHandler
      public void handler(ChooseOrderPaymentTypeEvent event) {
          if (paymentId == null) {
              CreateReceiptPaymentEvent createReceiptPaymentEvent = new CreateReceiptPaymentEvent();
              createReceiptPaymentEvent.setId(UUID.randomUUID().toString());
              createReceiptPaymentEvent.setOrderId(orderId);
              createReceiptPaymentEvent.setPaymentType(event.getPaymentType());
              createReceiptPaymentEvent.setAmount(amount);
              sagaGateway.send(createReceiptPaymentEvent);

          } else {
              TryAgainReceiptPaymentEvent againReceiptPaymentEvent = new TryAgainReceiptPaymentEvent();
              againReceiptPaymentEvent.setId(paymentId);
              againReceiptPaymentEvent.setPaymentType(event.getPaymentType());
              sagaGateway.send(againReceiptPaymentEvent);
          }
      }

      @SagaOrchestEventHandler
      public void handler(OrderPaymentEntityCreatedEvent event) {
          this.paymentId = event.getPaymentId();
          this.paymentType = event.getPaymentType();

          orderService.updateOrder(event);
          orderPushNotification.sentClientNotification(event.getId(), "PAYMENT ID:[ " + paymentId + " ] " 
          + paymentType.name() + " SUCCESSFUL SELECTED. YOU CAN PAY FROM IT'S PAYMENT TYPE");
      }

      @SagaOrchestEnd
      @SagaOrchestEventHandler
      public void handler(SuccessOrderEvent event) {
          orderService.orderSuccessfulPaymentDone(event);
          orderPushNotification.sentClientNotification(event.getId(), "ORDER SUCCESSFUL PAYMENT DONE.");
      }

      @SagaOrchestEnd
      @SagaOrchestEventHandler
      public void handler(RollbackOrderEvent event) {
          orderService.orderProcessFail(event);

          if(!event.isCallPaymentSaga()) {
              RollbackPaymentEvent rollbackPaymentEvent = new RollbackPaymentEvent();
              rollbackPaymentEvent.setId(paymentId);
              rollbackPaymentEvent.setCallOrderSaga(true);
              sagaGateway.send(rollbackPaymentEvent);

          }
          orderPushNotification.sentClientNotification(event.getId(), "ORDER PROCESS FAIL AND ROLLBACK");
      }


      @SagaOrchestException
      public void exceptionHandler(SagaExceptionHandler sagaExceptionHandler){
          orderPushNotification.sentClientNotification(sagaExceptionHandler.getSagaId(),
                  "ORDER EXCEPTION IN EVENT CLASS"+sagaExceptionHandler.getExceptionEventClass()
                          +" SAGA METHOD NAME "+ sagaExceptionHandler.getExceptionSagaMethodName() 
                          +"EXCEPTION MESSAGE :["+sagaExceptionHandler.getException().getMessage()+"]");

          RollbackOrderEvent rollbackOrderEvent = new RollbackOrderEvent();
          rollbackOrderEvent.setId(orderId);
          sagaGateway.send(rollbackOrderEvent);
      }
  }
```

#### @SagaOrchestEventHandler
Declaration with annotation method will be handle from sent  **sagaGateway**  bean events.

1.Send
```java
  @PostMapping(value = "/choose-payment-type")
  public @ResponseBody String createOrder(@RequestBody ChooseOrderPaymentTypeEvent event) {
      sagaGateway.send(event);
      return event.getId();
  }
```
2. Handle
```java
    @SagaOrchestEventHandler
    public void handler(ChooseOrderPaymentTypeEvent event) {
      if (paymentId == null) {
          CreateReceiptPaymentEvent createReceiptPaymentEvent = new CreateReceiptPaymentEvent();
          createReceiptPaymentEvent.setId(UUID.randomUUID().toString());
          createReceiptPaymentEvent.setOrderId(orderId);
          createReceiptPaymentEvent.setPaymentType(event.getPaymentType());
          createReceiptPaymentEvent.setAmount(amount);
          sagaGateway.send(createReceiptPaymentEvent);

      } else {
          TryAgainReceiptPaymentEvent againReceiptPaymentEvent = new TryAgainReceiptPaymentEvent();
          againReceiptPaymentEvent.setId(paymentId);
          againReceiptPaymentEvent.setPaymentType(event.getPaymentType());
          sagaGateway.send(againReceiptPaymentEvent);
      }
    }
```

#### @SagaOrchestStart
Declaration annotation start point method of the Saga class. Maybe declaration more than two.
```java
  @SagaOrchestStart
  @SagaOrchestEventHandler
  public void handler(CreateOrderEvent event){
      this.orderId = event.getId();
      this.itemsId = event.getItems();

      orderService.createOrder(event, amount);
      orderPushNotification.sentClientNotification(event.getId(), 
      "YOUR ORDER CREATED, PLEASE SELECT PAYMENT TYPE AND PAY FROM IT : [ " + PaymentType.getStrings()+" ]");
  }
```
#### @SagaOrchestEnd
Declaration annotation finish/ended point method of the Saga class. Maybe declaration more than two.
```java
  @SagaOrchestEnd
  @SagaOrchestEventHandler
  public void handler(SuccessOrderEvent event) {
      orderService.orderSuccessfulPaymentDone(event);
      orderPushNotification.sentClientNotification(event.getId(), "ORDER SUCCESSFUL PAYMENT DONE.");
  }
```
#### @SagaOrchestException
Declaration annotation any exception to handle of the Saga class. Must is declaration one time in the saga class. Maybe two option **@SagaOrchestException**  declaration method.
1. With attribute **uz.kassa.microservice.saga.event.SagaExceptionHandler**
```java
  @SagaOrchestException
  public void exceptionHandler(SagaExceptionHandler sagaExceptionHandler){
      orderPushNotification.sentClientNotification(sagaExceptionHandler.getSagaId(),
              "ORDER EXCEPTION IN EVENT CLASS"+sagaExceptionHandler.getExceptionEventClass()
                      +" SAGA METHOD NAME "+ sagaExceptionHandler.getExceptionSagaMethodName() +
                      "EXCEPTION MESSAGE :["+sagaExceptionHandler.getException().getMessage()+"]");

      RollbackOrderEvent rollbackOrderEvent = new RollbackOrderEvent();
      rollbackOrderEvent.setId(orderId);
      sagaGateway.send(rollbackOrderEvent);
  }
```
2. Without attribute. 
```java
  @SagaOrchestException
  public void exceptionHandler(){
      RollbackPaymentEvent rollbackOrderEvent = new RollbackPaymentEvent();
      rollbackOrderEvent.setId(paymentId);
      sagaGateway.send(rollbackOrderEvent);
  }
```
## SagaGateway
This is bean realization to send events to Saga instance and run it. Actually under the bean hidden big logic. What does **SagaGateway** do?. It get POJO class and send to **Kafka** another framework bean listen **Kafka** and consumer events and redirect to **Saga** Instance.
```java
   sagaGateway.send(againReceiptPaymentEvent);
```
