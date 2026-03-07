package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import com.bruno.ecommerce.components.SearchComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * MyAccountPage for the Ecommerce Playground.
 */
public class MyAccountPage extends BasePage {

    private final By editAccountLink = By.xpath("//a[text()='Edit your account information']");
    
    private final SearchComponent searchComponent;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        this.searchComponent = new SearchComponent(driver);
    }

    @Step("Verify if logged in successfully")
    public boolean isUserLoggedIn() {
        return isDisplayed(editAccountLink);
    }

    public SearchComponent searchComponent() {
        return searchComponent;
    }
}
