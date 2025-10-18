# ========================
# Stage 1: Build the application
# ========================
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

RUN apk add --no-cache libc6-compat gcompat libstdc++

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew dependencies --no-daemon || true

COPY src ./src

RUN find /root/.gradle -type f -name "protoc-gen-grpc-java*" -exec chmod +x {} + || true

RUN ./gradlew bootJar --no-daemon --build-cache --parallel

# ========================
# Stage 2: Run the application
# ========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
