FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENTRYPOINT [ "sh", "-c", "java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Djava.security.egd=file:/dev/./urandom -jar hollow.jar *.war" ]

EXPOSE 8080

ADD target/weight-skinny-1.0-hollow-swarm.jar hollow.jar
ADD target/weight-skinny-1.0.war app.war

