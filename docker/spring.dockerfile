FROM openjdk:17-alpine AS builder

WORKDIR /app

COPY backend/spring/build/libs/*.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]