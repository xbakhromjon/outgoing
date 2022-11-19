FROM openjdk:11
ADD target/outgoing.jar outgoing.jar
ENTRYPOINT ["java", "-jar","outgoing.jar"]
EXPOSE 8080