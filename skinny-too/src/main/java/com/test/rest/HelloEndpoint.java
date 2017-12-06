package com.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/greeting")
public class HelloEndpoint {

    @GET
    public String hello() {
        return "hello from WildFly Swarm Skinny TOO!";
    }

}
