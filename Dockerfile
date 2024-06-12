# syntax=docker/dockerfile:1
FROM eclipse-temurin:21

# Copy project files to the container
COPY . .

# Build the gradle project
RUN ./gradlew build

# Run program, which will expect the token and channel as environment variables
CMD ./gradlew run
