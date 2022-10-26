package com.alexlis.config;

import org.aeonbits.owner.Config;

/**
 * var ${name} used by TestBase in ability to switch properties files
 */
@MainConfig.Sources({
        "classpath:config.properties"
})
public interface MainConfig extends Config {

    @Key("demoWebShop.web.url")
    String getWebUrlUrl();

    @Key("demoWebShop.api.uri")
    String getApiUri();

    @Key("demoWebShop.login")
    String getUserLogin();

    @Key("demoWebShop.password")
    String getUserPassword();

    @Key("name.browser")
    String getBrowserName();

    @Key("browser.size")
    String getBrowserSize();
}
