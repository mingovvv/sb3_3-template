# Stage 1: Build the application
FROM docker.io/library/openjdk:17-jdk-alpine as builder

WORKDIR /app

COPY build.gradleX .
COPY settings.gradle .
COPY gradle gradle
COPY gradlew .
COPY src src

RUN ./gradlew build --no-daemon

# Stage 2: Run the application
FROM docker.io/library/openjdk:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/sb3_3-template-0.0.1-SNAPSHOT.jar /app/sb3_3-template-0.0.1-SNAPSHOT.jar

CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-local}", "-jar", "/app/sb3_3-template-0.0.1-SNAPSHOT.jar"]