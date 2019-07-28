# Getting Started

### Building project

Run command:
>mvn clean install

### Run service

Go to 'packet-service' directory and start exchange service development zone by run command:
>mvn spring-boot:run -Dspring-boot.run.profiles=DZTZ

After start open URL 'http://localhost:8081/swagger-ui.html' to manage REST API


Then start testing zone service by run command:  
>mvn spring-boot:run -Dspring-boot.run.profiles=TZDZ

After start open URL 'http://localhost:8082/swagger-ui.html' to manage REST API

### Scenario

1. Create new outgoing packet (incomming=flase) in development zone via Admin API
2. Get packet id
3. Wait 5 seconds
4. Check packets states by id in development zone (should be PLACED, SENT, DELIVERED)
5. Get packet by this id in testing zone (should be incoming=true and the same payload)
6. Check packets states by id in testing zone (should be RECEIVED)
7. Update packets status to PROCED
8. Check packets states by id in testing zone (should be RECEIVED, PROCED)
9. Check packets states by id in development zone (should be PLACED, SENT, DELIVERED, PROCED)

That's all:)


