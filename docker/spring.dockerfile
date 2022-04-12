FROM openjdk:17-alpine

WORKDIR /app 

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/build/libs/back-0.0.1-SNAPSHOT.jar"]