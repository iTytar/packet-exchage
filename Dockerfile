FROM maven:3-jdk-11 as builder
# todo: cannot cmpile with maven:3-jdk-8
#[ERROR] /work/packet-core/src/main/java/net/tyt/packet/core/impexp/fs/FsSupport.java:[32,22] cannot find symbol
# symbol:   method of(java.lang.String,java.lang.String)
# location: interface java.nio.file.Path

ADD . /work
WORKDIR /work

RUN mvn clean install
#RUN cp -v /work/packet-service/target/packet-service-*-*.jar /work/packet-service/target/packet-service.jar

FROM openjdk:11
#FROM openjdk:8
# todo: cannot run on jre8, while compiled with jdk 11: https://github.com/spring-projects/spring-framework/issues/17242


COPY --from=builder /work/packet-service/target/packet-service-0.0.1-SNAPSHOT.jar /
CMD [ "java", "-jar", "/packet-service-0.0.1-SNAPSHOT.jar" ]

# build: docker build . -t tyt/packet-exchange:latest
# run:   docker run -it --rm --env spring.profiles.active=DZTZ -p 8081-8082:8081-8082 tyt/packet-exchange:latest

# run:   docker run -it --rm --env spring.profiles.active=DZTZ -p 8081:8081 tyt/packet-exchange:latest
# run:   docker run -it --rm --env spring.profiles.active=TZDZ -p 8082:8082 tyt/packet-exchange:latest
