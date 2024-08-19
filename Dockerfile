FROM maven:3.9.2-eclipse-temurin-17-alpine AS builder

COPY ./src src/
COPY ./pom.xml pom.xml
COPY lombok.config lombok.config

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

COPY --from=builder target/*.jar app.jar

EXPOSE 8080
CMD ["java","-jar","/app.jar"]