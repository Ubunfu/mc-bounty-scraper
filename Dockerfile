FROM maven:slim as build
WORKDIR /build
COPY . .
RUN ["mvn", "clean", "package", "-DskipTests"]

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /build/target/*.jar ./app.jar
ENV TAILER_LOGFILE server.log
ENV TAILER_READDELAYMILLIS 1000
ENV CLIENT_BOUNTYPROCESSOR_URL api-gateway.aws.com/bounty
ENTRYPOINT ["java", "-jar", "./app.jar"]