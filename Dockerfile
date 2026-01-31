# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/resume-analyser-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (Render sets PORT env var, typically 10000, but we use 8081 as default)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
