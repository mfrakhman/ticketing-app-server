# ===============================
# Stage 1: Build the application
# ===============================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ===============================
# Stage 2: Run the application
# ===============================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar from the builder
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (matches APP_PORT in .env)
EXPOSE ${APP_PORT}

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
