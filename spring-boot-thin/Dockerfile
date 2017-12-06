FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENTRYPOINT [ "sh", "-c", "java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Djava.security.egd=file:/dev/./urandom -jar app.jar --thin.root=. --thin.offline=true" ]

EXPOSE 8080

ADD target/thin/root/repository repository
ADD target/thin/root/greeting-spring-boot-thin.jar app.jar

