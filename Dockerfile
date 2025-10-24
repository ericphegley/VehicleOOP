#Build stage
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

#Copy pom.xml and download dependencies (leverage cache)
COPY pom.xml .
RUN mvn dependency:go-offline

#Copy source code
COPY src ./src

#Build the Spring Boot application
RUN mvn clean package 

#Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

#Copy the built JAR from the build stage
COPY --from=build /app/target/VehicleOOP-0.0.1-SNAPSHOT.jar /app/VehicleOOP.jar

#Expose the port the app will run on
EXPOSE 8080

#Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/VehicleOOP.jar"]
