FROM maven:3.9-amazoncorretto-20 as builder
WORKDIR /app
COPY . .

RUN mvn clean package


FROM openjdk:20-ea-19-oracle
LABEL authors="phong"

WORKDIR /app

COPY --from=builder /app/target/NotificationServer-0.0.1-SNAPSHOT.jar /app/NotificationServer-0.0.1-SNAPSHOT.jar
# Expose the port your Spring Boot application will run on
EXPOSE 8006

ENTRYPOINT ["java", "-jar", "NotificationServer-0.0.1-SNAPSHOT.jar"]