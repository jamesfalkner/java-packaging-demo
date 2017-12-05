package com.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.joda.time.LocalDate;

@Path("/greeting")
public class HelloEndpoint {

    @GET
    public String hello() {
        String type = "Java EE/WildFly Thin WAR";
        if (System.getProperty("swarm.app.name") != null) {
            type = "WildFly Swarm Far JAR";
        }
        return "hello from " + type + ", the date is " + LocalDate.now().toString();
    }

}
