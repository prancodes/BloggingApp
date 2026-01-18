# --- Stage 1: The Build Stage ---
# Use the latest Maven 3 (automatically pull latest 3.9+) image compatible with Java 25
FROM maven:3-eclipse-temurin-25-alpine AS builder

# Set the working directory in the container
WORKDIR /blogApp

# 1. Copy only the pom.xml to leverage Docker's layer caching.
# This step is only re-run if pom.xml changes.
COPY pom.xml .

# 2. Download all dependencies
RUN mvn dependency:go-offline

# 3. Copy your source code
COPY src ./src

# 4. Build the application. We skip tests for a production build.
# This will create 'project-1.0.0.jar' based on pom.xml
RUN mvn clean package -DskipTests


# --- Stage 2: The Final Runtime Stage ---
# Use the official Eclipse Temurin Java 25 JRE on Alpine Linux
FROM eclipse-temurin:25-jre-alpine

# --- LABELS ---
LABEL org.opencontainers.image.source="https://github.com/prancodes/BloggingApp"
LABEL org.opencontainers.image.title="BloggingProject"
LABEL org.opencontainers.image.description="A full-featured blogging platform built with Spring Boot 4, Thymeleaf, JPA, and MySQL."

# Set the working directory
WORKDIR /blogApp

# Create a non-root user and group for security
RUN addgroup -S appgroup && adduser -S bloguser -G appgroup

# Switch to the non-root user
USER bloguser:appgroup

# Copy the built .jar file from the 'builder' stage
# We rename it to blogApp.jar for simplicity
COPY --from=builder /blogApp/target/project-1.0.0.jar blogApp.jar

# Expose the port your application runs on, specified in your application.properties
EXPOSE 8080

# The command to run your application
ENTRYPOINT ["java", "-jar", "blogApp.jar"]
