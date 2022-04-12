FROM openjdk:17-alpine AS builder

WORKDIR /app


COPY backend/spring ./

RUN chmod +x gradlew

# RUN ./gradlew build -x test

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/build/libs/back-0.0.1-SNAPSHOT.jar"]