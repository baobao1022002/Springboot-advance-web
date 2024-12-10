
FROM gradle:8 AS builder
WORKDIR /home
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/
COPY src/ src/
RUN gradle clean bootJar

#second stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /home
COPY --from=builder /home/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

