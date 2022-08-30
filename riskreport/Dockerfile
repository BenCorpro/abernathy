FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/riskreport-0.0.1-SNAPSHOT.jar risk-report.jar
ENTRYPOINT ["java", "-jar", "/risk-report.jar"]
EXPOSE 8080
