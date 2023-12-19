# Stage 1: Build the application using Maven and OpenJDK 17
FROM maven:3.8-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with OpenJDK 17
FROM openjdk:17-jdk-slim
COPY --from=build target/jobfinder-0.0.1-SNAPSHOT.jar jobfinder.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","jobfinder.jar"]
