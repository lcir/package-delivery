FROM maven:3-openjdk-16-slim as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn clean package

FROM openjdk:16-slim
VOLUME /tmp
# Copy the jar to the production image from the builder stage.

COPY --from=builder /app/target/*.jar /app.jar
# COPY target/*.jar app.jar
EXPOSE 5005
ENTRYPOINT ["java","-jar","/app.jar"]
