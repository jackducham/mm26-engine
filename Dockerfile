FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# docker build --build-arg JAR_FILE=target/MM26GameEngine-0.0.1-SNAPSHOT.jar -t mm26/game-engine .
