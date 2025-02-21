FROM eclipse-temurin:17-jdk-alpine

LABEL author="anastasia-martova"

WORKDIR /app

COPY target/auth-microservice-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
