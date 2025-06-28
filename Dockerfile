FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:resolve

COPY src ./src
COPY target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
