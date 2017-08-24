package com.example.demo;

import com.example.demo.health.TemplateHealthCheck;
import com.example.demo.resources.Greeting;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfig> {

    public static void main(final String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "greeting";
    }

    @Override
    public void initialize(final Bootstrap<AppConfig> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final AppConfig configuration,
                    final Environment environment) {
        final Greeting resource = new Greeting(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
    }

}
