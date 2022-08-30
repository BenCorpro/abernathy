FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/patienthistory-0.0.1-SNAPSHOT.jar patient-histo.jar
ENTRYPOINT ["java", "-jar", "/patient-histo.jar"]
EXPOSE 8082