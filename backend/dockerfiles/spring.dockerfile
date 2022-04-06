FROM openjdk:17-alpine

WORKDIR /app 

COPY spring/build/libs/*.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]