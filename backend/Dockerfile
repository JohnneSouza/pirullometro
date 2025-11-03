# =========================================================================
# STAGE 1: Build Stage
#
# This stage uses a full JDK and Maven to build the application .jar file.
# We use an explicit, deterministic tag for the official Eclipse Temurin (OpenJDK) image.
# =========================================================================
FROM maven:3.9.6-eclipse-temurin-21-alpine@sha256:9fca6934e64eff26860889804faecb71a522d04b4ebf05574140c71f8e9bc700 AS builder

# Set the working directory
WORKDIR /app

COPY pom.xml ./

# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application, skipping tests (run tests in your CI pipeline)
# The resulting .jar will be in /app/target/
RUN mvn package -DskipTests


# =========================================================================
# STAGE 2: Runtime Stage
#
# This stage uses a slim, JRE-only Alpine image to run the application.
# It copies *only* the built .jar from the 'builder' stage.
# =========================================================================
FROM eclipse-temurin:21.0.4_7-jre-alpine

# Set the working directory
WORKDIR /app

# Define an argument for the JAR file name (defaults to a wildcard)
# This makes it easy to find the .jar file from the build stage.
ARG JAR_FILE=/app/target/*.jar

# --- Security: Run as a non-root user ---
# 1. Create a dedicated group and user (use -S for system user/group)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 2. Change ownership of the app directory
# This isn't strictly needed here as we USER first, but it's good practice
# if you were copying files *before* switching users.
# RUN chown -R appuser:appgroup /app

# 3. Switch to the non-root user
USER appuser

# Copy the built .jar file from the 'builder' stage
COPY --from=builder ${JAR_FILE} app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# --- Signal Handling & Execution ---
# Use the "exec" form of ENTRYPOINT/CMD.
# This ensures the Java process runs as PID 1 and can directly receive
# signals like SIGTERM (from `docker stop`), allowing for graceful shutdown.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]