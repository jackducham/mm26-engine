FROM openjdk:8-jdk-alpine as build
RUN apk add --no-cache maven

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Skip tests when building and use less verbose logging
RUN mvn install -DskipTests -B
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","mech.mania.engine.entrypoints.Main","--infraPort=8080"]

# docker build -t mm26/engine .
# docker run mm26/engine:latest
