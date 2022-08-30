FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/patientinfo-0.0.1-SNAPSHOT.jar patient-info.jar
ENTRYPOINT ["java", "-jar", "/patient-info.jar"]
EXPOSE 8081 