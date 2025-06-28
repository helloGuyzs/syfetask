FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

RUN sed -i 's/\r$//' mvnw && \
    chmod +x mvnw && \
    ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
