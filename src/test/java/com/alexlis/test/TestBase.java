package com.alexlis.test;

import com.alexlis.config.MainConfig;
import com.alexlis.helper.Pages;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    Pages pages = new Pages();

    @BeforeAll
    public static void setting() {

        MainConfig config = ConfigFactory.create(MainConfig.class, System.getProperties());

        RestAssured.baseURI = config.getApiUri();
        Configuration.baseUrl = config.getWebUrlUrl();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.browser = config.getBrowserName();
    }
}
