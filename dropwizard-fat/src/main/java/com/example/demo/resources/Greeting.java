package com.example.demo.resources;

import com.codahale.metrics.annotation.Timed;
import org.joda.time.LocalDate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

// start with java -jar target/greeting-dropwizard-1.0.0.jar server ./greeting.yml
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class Greeting {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public Greeting(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Path("/greeting")
    public com.example.demo.api.Greeting sayHello(@QueryParam("name") Optional<String> name) {
        final String greeting = String.format(template, name.orElse(defaultName));
        return new com.example.demo.api.Greeting(counter.incrementAndGet(), greeting + ", the date is " + LocalDate.now().toString());
    }
}
