FROM openjdk:11.0-jre-slim-buster

EXPOSE 8080

USER 1001

CMD ["java",  "-jar", "/keeper.jar"]

COPY ./keeper/target/keeper*.jar /keeper.jar
