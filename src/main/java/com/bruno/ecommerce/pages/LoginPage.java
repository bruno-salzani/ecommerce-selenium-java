package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage for the Ecommerce Playground.
 */
public class LoginPage extends BasePage {

    private final By emailInput = By.id("input-email");
    private final By passwordInput = By.id("input-password");
    private final By loginButton = By.xpath("//input[@value='Login']");
    private final By alertMessage = By.cssSelector(".alert-danger");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get Alert Message text")
    public String getAlertMessage() {
        return getText(alertMessage);
    }

    @Step("Fill Email: {email}")
    public LoginPage fillEmail(String email) {
        sendKeys(emailInput, email);
        return this;
    }

    @Step("Fill Password: {password}")
    public LoginPage fillPassword(String password) {
        sendKeys(passwordInput, password);
        return this;
    }

    @Step("Click Login Button")
    public MyAccountPage clickLogin() {
        click(loginButton);
        return new MyAccountPage(driver);
    }

    @Step("Login with credentials: {email}")
    public MyAccountPage login(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        return clickLogin();
    }
}
