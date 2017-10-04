FROM vertx/vertx3-exec

ENV VERTICLE_NAME com.example.demo.App
ENV VERTICLE_HOME /usr/verticles

ENTRYPOINT [ "sh", "-c", "vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*" ]

EXPOSE 8080

ADD target/greeting-vertx-1.0.0.jar $VERTICLE_HOME/

