package com.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.joda.time.LocalDate;

@Path("/hello")
public class HelloEndpoint {

    @GET
    public String hello() {
        return "hello from WildFly Swarm Skinny JAR, the date is " + LocalDate.now().toString();
    }

}
