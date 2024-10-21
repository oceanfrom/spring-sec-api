FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/SecurityApp-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
