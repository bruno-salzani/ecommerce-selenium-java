package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SearchPage for the Ecommerce Playground.
 */
public class SearchPage extends BasePage {

    private final By firstProductLink = By.xpath("(//div[@class='product-thumb']//a)[1]");
    private final By noResultsMessage = By.xpath("//p[contains(text(),'There is no product that matches the search criteria.')]");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify if no results message is displayed")
    public boolean isNoResultsMessageDisplayed() {
        return isDisplayed(noResultsMessage);
    }

    @Step("Click on the first product in search results")
    public ProductPage clickFirstProduct() {
        click(firstProductLink);
        return new ProductPage(driver);
    }
}
