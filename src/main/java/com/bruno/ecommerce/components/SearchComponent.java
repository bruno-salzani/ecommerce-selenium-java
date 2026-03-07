package com.bruno.ecommerce.components;

import com.bruno.ecommerce.base.BasePage;
import com.bruno.ecommerce.pages.SearchPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Component for the Search Bar, which is shared across multiple pages.
 */
public class SearchComponent extends BasePage {

    private final By searchInput = By.name("search");
    private final By searchButton = By.xpath("//button[text()='Search']");

    public SearchComponent(WebDriver driver) {
        super(driver);
    }

    @Step("Search for product using the shared search component: {productName}")
    public SearchPage search(String productName) {
        sendKeys(searchInput, productName);
        click(searchButton);
        return new SearchPage(driver);
    }
}
