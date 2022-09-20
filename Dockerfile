FROM openjdk:17-oracle
MAINTAINER RainbowJM
COPY ./target/tennis-0.0.1-SNAPSHOT.jar tennis.jar
ENTRYPOINT ["java", "-jar", "tennis.jar"]