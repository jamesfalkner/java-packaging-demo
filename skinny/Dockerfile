FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar hollow.jar app.war" ]

EXPOSE 8080

ADD target/weight-1.0-hollow-swarm.jar hollow.jar
ADD target/weight-1.0.war app.war

