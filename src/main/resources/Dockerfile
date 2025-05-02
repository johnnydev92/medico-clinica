FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/clinica.medica-0.0.1-SNAPSHOT.jar  /app/clinica.medica.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/clinica.medica.jar"]