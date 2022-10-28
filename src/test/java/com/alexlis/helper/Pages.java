package com.alexlis.helper;

import com.alexlis.tests.TestBase;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Pages extends TestBase {

    private SelenideElement newPasswordRaw = $("#NewPassword"),
            confirmPasswordRaw = $("#ConfirmNewPassword"),
            changePasswordButton = $(".change-password-button"),
            fieldValidation = $(".field-validation-error"),
            email = $("#Email"),
            saveCustomerButton = $(".save-customer-info-button");

    @Step("Open page")
    public Pages openPage(String url) {
        open(url);
        return this;
    }

    @Step("Set new password and press confirm button")
    public Pages setNewPassword(String password, String confirmPassword) {
        newPasswordRaw.setValue(password);
        confirmPasswordRaw.setValue(confirmPassword);
        changePasswordButton.click();
        return this;
    }

    @Step("Check for error text")
    public Pages fieldValidate() {
        fieldValidation.shouldHave(Condition.text("Old password is required.)"));
        return this;
    }

    @Step("Set null email input")
    public Pages clearInput() {
        email.setValue("");
        saveCustomerButton.click();
        return this;
    }

    @Step("Check for error text")
    public Pages fieldValidationCheck() {
        fieldValidation.shouldHave(Condition.text("Email is required."));
        return this;
    }
}
