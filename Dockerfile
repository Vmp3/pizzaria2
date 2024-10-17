# Build stage
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY backend/pizzaria/src ./src
COPY backend/pizzaria/pom.xml .

RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar pizzaria.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/pizzaria.jar"]