# Packaging Java Applications

You've heard of Fat/Uber JARs and are probably building them today. But every time you build one,
a tree is chopped down in the forest to power the compute, disk and networking resources needed
to deploy it to your production systems. Using this code you can explore the benefits and costs
of Fat JAR packaging and exercise various options for slimming your apps and saving those trees
using popular frameworks like [Wildfly Swarm](http://wildfly-swarm.io), [Dropwizard](http://www.dropwizard.io/),
[Spring Boot](https://projects.spring.io/spring-boot/) and [Eclipse Vert.x](http://vertx.io).

## Fat (Uber) JARs

Maven and in particular Spring Boot popularized this well-known approach to packaging, which
includes everything needed to run the whole app on a standard Java Runtime environment
(i.e. so you can run the app with `java -jar myapp.jar`). The amount of extra runtime stuff
included in the Uberjar (and its file size) depends on the framework and runtime features
your app uses.

## How to use this repository

Just clone it:

`git clone https://github.com/jamesfalkner/java-packaging-demo`

And then play along below by following the steps for each technology:

### WildFly Swarm Fat JAR

1. Build the app:
    ```bash
    cd wildfly-swarm-fat-thin
    mvn clean package
    ```
1. Run Fat JAR with `java -jar target/weight-1.0-swarm.jar`

1. Then access using `http://localhost:8080/api/greeting`

1. You should see something like `hello from WildFly Swarm Far JAR, the date is 2017-12-07`
1. Type CTRL-C to stop it.

### Spring Boot Fat JAR

1. Build the app:
    ```bash
    cd spring-boot-fat
    mvn clean package
    ```

1. Run Fat JAR with `java -jar target/greeting-spring-boot-fat.jar`

1. Then access using `http://localhost:8080/api/greeting`

1. Type CTRL-C to stop it.

### Dropwizard Fat JAR

1. Build the app:
    ```bash
    cd dropwizard-fat
    mvn clean package
    ```

1. Run Fat JAR with `java -jar target/greeting-dropwizard-1.0.0.jar server ./greeting.yml`

1. Then access using `http://localhost:8080/api/greeting`

1. Type CTRL-C to stop it.

### Eclipse Vert.x Fat JAR

1. Build the app:
    ```bash
    cd vertx-fat
    mvn clean package
    ```

1. Run Fat JAR with `java -jar target/greeting-vertx-fat-1.0.0-fat.jar`

1. Then access using `http://localhost:8080/api/greeting`

1. Type CTRL-C to stop it.

Exciting, eh?

## Thin WARs/JARs

If you’re a Java EE developer, chances are you’re already doing this. It is what you have been doing
for over a decade, so congratulations you’re still cool! A thin WAR is a Java EE web application
that only contains the web content and business logic you wrote, along with 3rd-party dependencies.
It does not contain anything provided by the Java EE runtime, hence it’s “thin” but it cannot run
“on its own” – it must be deployed to a Java EE app server or Servlet container that contains
the “last mile” of bits needed to run the app on the JVM.

### WildFly Swarm Thin WAR

1. Build the thin WAR:
    ```bash
    cd wildfly-swarm-fat-thin
    mvn clean package
    ```

The Thin WAR is located at `target/weight-1.0.war` and can be deployed to Java EE app servers like [WildFly](http://wildfly.org)

For example, to download and run WildFly 11 with the Thin WAR deployed to it:

```bash
TMPDIR=`mktemp -d`
curl -skl http://download.jboss.org/wildfly/11.0.0.Final/wildfly-11.0.0.Final.tar.gz | (cd $TMPDIR; tar -xvzf -)
cp target/weight-1.0.war $TMPDIR/wildfly-11.0.0.Final/standalone/deployments
$TMPDIR/wildfly-11.0.0.Final/bin/standalone.sh
```

Then access with `http://localhost:8080/api/greeting`

Type CTRL-C to stop it.

### Thin JAR with Spring Boot

This variant uses the [Spring Boot Thin Launcher](https://github.com/dsyer/spring-boot-thin-launcher) which is a
Maven plugin for building thin JARs. See its usage in [the POM file](/spring-boot-thin/pom.xml#L62)

1. Build the thin JAR:
    ```bash
    cd spring-boot-thin
    mvn clean package
    ```
1. Run thin jar with `java -jar target/thin/root/greeting-spring-boot-thin.jar`. This Thin JAR finds its dependencies automatically in the `thin/root/repository` directory.
1. Then access `http://localhost:8080/api/greeting`
1. Type CTRL-C to stop it.

To build a docker image with the thin JAR in a separate layer:

1. Build the image with `docker build -t spring-thin:latest .`
1. Notice that the container image is split in two separate layers: The `thin/root/repository` directory containing app dependencies, and the app itself (Thin JAR) in `target/thin/root/greeting-spring-boot-thin.jar`:
    ```console
    ....
    Step 5/6 : ADD target/thin/root/repository repository
     ---> 2834ca524f63
    Step 6/6 : ADD target/thin/root/greeting-spring-boot-thin.jar app.jar
     ---> 041f07b12aca
    Successfully built 041f07b12aca
    Successfully tagged spring-thin:latest
    ```
1. Make a change to the app source code to simulate a simple change, for example
    ```
    echo 'class foo {}' >> src/main/java/com/example/demo/BoosterApplication.java
    ```
1. Re-build application with `mvn clean package`
1. Re-build docker image with `docker build -t spring-thin:latest .`
1. Notice that the previously created dependency layer previously created from `thin/root/repository` is cached and re-used, saving time and energy on redeploys to your production clusters:
    ```console
    Step 6/7 : ADD target/thin/root/repository repository
     ---> Using cache   <--- *** Cached!
     ---> 2834ca524f63
    Step 7/7 : ADD target/thin/root/greeting-spring-boot-thin.jar app.jar
     ---> 8b4e5ac9cd69  <--- *** Not cached
    Successfully built 8b4e5ac9cd69
    Successfully tagged spring-thin:latest
    ```

### Thin JAR with Eclipse Vert.x

This variant excludes the Vert.x dependencies from the app using `<scope>provided</scope>`. There's probably a way to
do it better with the [Maven Shade plugin](https://maven.apache.org/plugins/maven-shade-plugin/).

The app is then combined with a pre-built docker base image containing the Vert.x runtime (thereby achieving its goal of
separating app from runtime, in two separate container layers):

1. Build the thin JAR:
    ```bash
    cd vertx-thin
    mvn clean package
    ```
1. Attempt to run thin jar with `java -jar target/greeting-vertx-1.0.0.jar`. **This will fail** due to the dependencies (Vert.x runtime) unable to be located.

To build a docker image with the thin JAR in a separate layer:

1. Build the image with `docker build -t vertx-thin:latest .`
1. Notice that the container image is split in two separate layers. The first layer `Step 1/6 : FROM vertx/vertx3-exec` contains the Vert.x runtime and is downloaded
from Docker Hub. The last layer `Step 6/6 : ADD target/greeting-vertx-1.0.0.jar $VERTICLE_HOME/` contains the Thin JAR of the application.
1. Make a change to the app source code to simulate a simple change, for example
    ```
    echo 'class foo {}' >> src/main/java/com/example/demo/BoosterApplication.java
    ```
1. Re-build application with `mvn clean package`
1. Re-build docker image with `docker build -t vertx-thin:latest .`
1. Notice that the only un-cached layer is the thin JAR, saving time and energy on redeploys to your production clusters!

Now let's really get something going by going skinny!

### Skinny WAR with WildFly Swarm

You may have noticed that the Thin WAR created by WildFly Swarm is still ~500k, not because the project has
500k of code in it, but because it depends on a simple library (the [Joda Time library](http://www.joda.org/joda-time/) ). You can see it here:

```bash
% unzip -l wildfly-swarm-fat-thin/target/weight-1.0.war|grep joda
   634048  00-00-80 04:08   WEB-INF/lib/joda-time-2.9.9.jar
```

In earlier examples with Spring Boot, this library was placed alongside all the other libraries in a flat classpath, including Spring Boot itself,
which provides convenience but requires special code if you want to isolate the app's classes from its runtime. WildFly Swarm has its
WildFly heritage which means it uses classloader isolation to achieve protection but requires special handling to properly link external libraries to
applications.

To remove the library and get down to the smallest possible app, you'll need to build a [WildFly Swarm Fraction](http://docs.wildfly-swarm.io/2017.11.0/#fractions) which will contain
the necessary bits (which allow it to properly hook into the app at runtime, using [JBoss Modules](https://jboss-modules.github.io/jboss-modules/manual/) ). The
fraction is then used when building a [Hollow JAR](http://docs.wildfly-swarm.io/2017.11.0/#hollow-jar), which is an executable (but initially useless) WildFly Swarm runtime
to which the skinny app can be deployed.


1. To build the Fraction:
    ```bash
    cd joda-fraction
    mvn clean package install
    ```
    This will install the Fraction to your local Maven repository (~/.m2/repository)
1. Next, build the Hollow JAR which will be able to run it:
    ```bash
    cd wildfly-swarm-skinny
    mvn clean package
    ```
    This will result in the Hollow JAR at `target/weight-skinny-1.0-hollow-swarm.jar` and the Skinny WAR at `target/weight-skinny-1.0.war`.
1. Finally, run the Hollow JAR, passing it the path to the Skinny WAR to deploy:
    ```bash
    java -jar target/weight-skinny-1.0-hollow-swarm.jar target/weight-skinny-1.0.war
    ```
1. Then access using `http://localhost:8080/api/greeting`
1. Press CTRL-C to stop it

To build a docker image with the hollow JAR and skinny WAR in a separate layer:

1. Build the image with `docker build -t swarm-thin:latest .`
1. Notice that the container image is split in two separate layers, one for the app, and one for the hollow JAR.
1. Make a change to the app source code to simulate a simple change, for example
    ```
    echo 'class foo {}' >> src/main/java/com/test/rest/HelloEndpoint.java
    ```
1. Re-build application with `mvn clean package`
1. Re-build docker image with `docker build -t swarm-thin:latest .`
1. Notice that just as in the Spring Boot example that the only re-built layer is the one containing the (2kb) skinny WAR.

## Resources

* [A blog post detailing more](https://developers.redhat.com/blog/2017/08/24/the-skinny-on-fat-thin-hollow-and-uber/)
