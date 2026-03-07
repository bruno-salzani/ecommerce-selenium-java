package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By firstNameInput = By.id("input-firstname");
    private final By lastNameInput = By.id("input-lastname");
    private final By emailInput = By.id("input-email");
    private final By telephoneInput = By.id("input-telephone");
    private final By passwordInput = By.id("input-password");
    private final By passwordConfirmInput = By.id("input-confirm");
    private final By subscribeYes = By.xpath("//label[@for='input-newsletter-yes']");
    private final By subscribeNo = By.xpath("//label[@for='input-newsletter-no']");
    private final By privacyPolicy = By.xpath("//label[@for='input-agree']");
    private final By continueButton = By.xpath("//input[@value='Continue']");
    private final By successMessage = By.xpath("//h1[text()='Your Account Has Been Created!']");
    private final By warningMessage = By.cssSelector(".alert-danger");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill Registration Form")
    public RegisterPage fillRegistrationForm(String firstName, String lastName, String email, String telephone, String password, boolean subscribe) {
        sendKeys(firstNameInput, firstName);
        sendKeys(lastNameInput, lastName);
        sendKeys(emailInput, email);
        sendKeys(telephoneInput, telephone);
        sendKeys(passwordInput, password);
        sendKeys(passwordConfirmInput, password);
        
        if (subscribe) {
            click(subscribeYes);
        } else {
            click(subscribeNo);
        }
        
        click(privacyPolicy);
        return this;
    }

    @Step("Submit Registration Form")
    public void submit() {
        click(continueButton);
    }

    @Step("Verify Registration Success")
    public boolean isRegistrationSuccessful() {
        return isDisplayed(successMessage);
    }

    @Step("Get Warning Message")
    public String getWarningMessage() {
        return getText(warningMessage);
    }
}
