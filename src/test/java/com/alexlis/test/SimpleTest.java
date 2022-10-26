package com.alexlis.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class SimpleTest extends TestBase {

    @Test
    void badPasswordChangeRequestTest() {
        step("Get cookie by api and set it to browser", () -> {
            String authorizationCookie =
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", "harry@te.st")
                            .formParam("Password", "qwerty")
                            .when()
                            .post("/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie to browser", () ->
                    getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", authorizationCookie)));
        });

        pages.openPage("/customer/changepassword")
                .setNewPassword("1234", "1234")
                .fieldValidate();
    }

    @Test
    void editingUserPageTest() {
        step("Get cookie by api and set it to browser", () -> {
            String authorizationCookie =
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", "harry@te.st")
                            .formParam("Password", "qwerty")
                            .when()
                            .post("/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie to browser", () ->
                    getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", authorizationCookie)));
        });

        pages.openPage("/customer/info")
                .clearInput()
                .fieldValidationCheck();
    }
}
