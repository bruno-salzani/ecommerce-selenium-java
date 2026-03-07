package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import com.bruno.ecommerce.components.SearchComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * HomePage for the Ecommerce Playground.
 */
public class HomePage extends BasePage {

    private final By myAccountMenu = By.xpath("//span[contains(text(),'My account')]");
    private final By loginSubMenu = By.xpath("//span[contains(text(),'Login')]");
    private final By registerSubMenu = By.xpath("//span[contains(text(),'Register')]");
    private final By cartLink = By.xpath("//a[contains(@href, 'route=checkout/cart')]");
    
    private final SearchComponent searchComponent;

    public HomePage(WebDriver driver) {
        super(driver);
        this.searchComponent = new SearchComponent(driver);
    }

    @Step("Inject session cookies and refresh")
    public HomePage injectSessionCookies(Map<String, String> cookies) {
        injectCookies(cookies);
        return this;
    }

    @Step("Navigate to Home Page")
    public HomePage open() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        return this;
    }

    public SearchComponent searchComponent() {
        return searchComponent;
    }

    @Step("Navigate to Login Page via menu")
    public LoginPage navigateToLogin() {
        click(myAccountMenu);
        click(loginSubMenu);
        return new LoginPage(driver);
    }

    @Step("Navigate to Register Page via menu")
    public RegisterPage navigateToRegister() {
        click(myAccountMenu);
        click(registerSubMenu);
        return new RegisterPage(driver);
    }

    @Step("Navigate to Cart Page")
    public CartPage navigateToCart() {
        click(cartLink);
        return new CartPage(driver);
    }
}
