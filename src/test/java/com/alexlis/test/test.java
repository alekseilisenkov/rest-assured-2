package com.alexlis.test;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class test {

    @BeforeAll
    public static void setting() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
        Configuration.browserSize = "1980x1920";
    }

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

        step("Open change password page", () ->
                open("/customer/changepassword"));

        step("Set new password and press confirm button", () -> {
            $("#NewPassword").setValue("1234");
            $("#ConfirmNewPassword").setValue("1234");
            $(".change-password-button").click();
        });

        step("Check for error text", () ->
                $(".field-validation-error").shouldHave(text("Old password is required.")));
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

        step("Open profile page", () ->
                open("/customer/info"));

        step("Clear email input", () -> {
            $("#Email").setValue("");
            $(".save-customer-info-button").click();
        });

        step("Check for error text", () ->
                $(".field-validation-error span").shouldHave(text("Email is required.")));
    }

    /**
     * Just example of usage restAssured
     */
    @Test
    void apiSendExample() {
        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product_attribute_72_5_18=53&product_attribute_72_6_19=54&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1")
                        .cookie("Nop.customer=d6b63c1a-40e8-414c-886c-e532c60ee78d;")
                        .when()
                        .post("https://demowebshop.tricentis.com/addproducttocart/details/72/1")
                        .then()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                        .body("updatetopcartsectionhtml", is("(21)"))
                        .extract().response();
    }
}
