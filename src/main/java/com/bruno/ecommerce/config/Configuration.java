package com.bruno.ecommerce.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "system:properties",
    "system:env",
    "file:src/test/resources/config/general.properties"
})
public interface Configuration extends Config {

    @Key("target")
    @DefaultValue("local")
    String target();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("headless")
    @DefaultValue("false")
    Boolean headless();

    @Key("url.base")
    @DefaultValue("https://ecommerce-playground.lambdatest.io/")
    String baseUrl();

    @Key("timeout")
    @DefaultValue("15")
    int timeout();

    @Key("grid.url")
    @DefaultValue("localhost")
    String gridUrl();

    @Key("grid.port")
    @DefaultValue("4444")
    String gridPort();
}
