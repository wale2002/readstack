# 1. Use official Java 21 runtime
FROM eclipse-temurin:21-jdk

# 2. Set working directory
WORKDIR /app

# 3. Copy Maven wrapper and project files
COPY . .

# 4. Give mvnw permission to execute (Linux requirement)
RUN chmod +x mvnw

# 5. Build the project — produces the JAR in /target
RUN ./mvnw clean package -DskipTests

# 6. Run the Spring Boot JAR
CMD ["java", "-jar", "target/book-buddy-backend-0.0.1-SNAPSHOT.jar"]
