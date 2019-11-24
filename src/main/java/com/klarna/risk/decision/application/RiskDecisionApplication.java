package com.klarna.risk.decision.application;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * An application configuration class. Provides information about packages
 * and applies dependency injection bindings
 */
public class RiskDecisionApplication extends ResourceConfig {

    public RiskDecisionApplication() {
        register(new RiskDecisionBinder());
        packages(true, "com.klarna.risk.decision");
    }

}
