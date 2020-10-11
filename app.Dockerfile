#FROM openjdk:11-jre-slim
FROM openjdk:11-jre

COPY ./target/challenger-microservice-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch challenger-microservice-0.0.1-SNAPSHOT.jar'


ENTRYPOINT ["java", "-jar", "challenger-microservice-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
