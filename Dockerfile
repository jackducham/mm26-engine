FROM openjdk:8-jdk-alpine
RUN apk add --no-cache maven

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Skip tests when building and use less verbose logging
RUN mvn install -DskipTests -B

ENTRYPOINT ["java","-jar","target/MM26GameEngine-0.0.1-SNAPSHOT.jar"]