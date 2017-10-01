FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar app.jar --thin.root=." ]

EXPOSE 8080

ADD target/thin/root/repository repository
ADD target/thin/root/greeting-spring-boot.jar app.jar

