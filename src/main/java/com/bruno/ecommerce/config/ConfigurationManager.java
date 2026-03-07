package com.bruno.ecommerce.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigurationManager {

    private static Configuration configuration;

    private ConfigurationManager() {}

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = ConfigFactory.create(Configuration.class);
        }
        return configuration;
    }
}
