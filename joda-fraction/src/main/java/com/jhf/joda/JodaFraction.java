
package com.jhf.joda;

import org.wildfly.swarm.config.runtime.AttributeDocumentation;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.annotations.Configurable;
import org.wildfly.swarm.spi.api.annotations.DeploymentModule;

@DeploymentModule(name = "org.jodaapi", slot = "main")
@Configurable("swarm.jodaapi")
public class JodaFraction implements Fraction<JodaFraction> {

    /**
     * Uses the specified connection info if not <code>null</code>. Otherwise
     * use primary Datasource
     */
    @AttributeDocumentation("Foo URL")
    private String fooUrl;

}
