# Getting Started

### Building project

Run command:
>mvn clean install

### Run service

Go to 'packet-service' directory and start exchange service development zone to testing zone by run command:
>mvn spring-boot:run -Dspring-boot.run.profiles=DZTZ

After start open URL 'http://localhost:8081/swagger-ui.html' to manage REST API


Then start testing zone service by run command:  
>mvn spring-boot:run -Dspring-boot.run.profiles=TZDZ

After start open URL 'http://localhost:8082/swagger-ui.html' to manage REST API


