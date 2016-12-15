package com.ofbizian.service.route;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;

@ContextName("smokeTest")
public class SampleRoute extends RouteBuilder {

    @Inject @Uri("jetty:http://0.0.0.0:8080/camel/test")
    private Endpoint jettyEndpoint;

    public SampleRoute() {
    }

    @Override
    public void configure() throws Exception {
        from(jettyEndpoint)
                .log(LoggingLevel.INFO, "new contact");
    }
}
