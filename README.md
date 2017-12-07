# Packaging Java Applications

You've heard of Fat/Uber JARs and are probably building them today. But every time you build one,
a tree is chopped down in the forest to power the compute, disk and networking resources needed
to deploy it to your production systems. Using this code you can explore the benefits and costs
of Fat JAR packaging and exercise various options for slimming your apps and saving those trees
using popular frameworks like [Wildfly Swarm](http://wildfly-swarm.io), [Dropwizard](http://www.dropwizard.io/),
[Spring Boot](https://projects.spring.io/spring-boot/) and [Eclipse Vert.x](http://vertx.io).

## WildFly Swarm

[WildFly Swarm](http://wildfly-swarm.io) offers an innovative approach to packaging and
running Java EE applications by
packaging them with just enough of the Java EE server runtime to be able to run them directly
on the JVM using **java -jar** For more details on various approaches to packaging Java
applications,
read [this blog post](https://developers.redhat.com/blog/2017/08/24/the-skinny-on-fat-thin-hollow-and-uber).

### WildFly Swarm Fat JAR


## Resources

* [Katacoda docs](https://www.katacoda.com/docs)
* [Status page](https://openshift.status.katacoda.com/)