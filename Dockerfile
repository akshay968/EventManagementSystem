# Stage 1: Build Stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application and build
COPY . .
RUN mvn package -DskipTests

# Stage 2: Runtime Stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/EventManagement-0.0.1-SNAPSHOT.jar EventManagement.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","EventManagement.jar"]