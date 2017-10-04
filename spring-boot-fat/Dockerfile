FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]

EXPOSE 8080

ADD target/greeting-spring-boot-fat.jar app.jar

