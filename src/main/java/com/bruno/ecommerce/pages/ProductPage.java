package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ProductPage for the Ecommerce Playground.
 */
public class ProductPage extends BasePage {

    private final By addToCartButton = By.xpath("//button[@title='Add to Cart']");
    private final By checkoutLink = By.xpath("//a[contains(text(),'Checkout')]");
    private final By successMessage = By.xpath("//div[contains(@class, 'alert-success')]");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify if product was added to cart success message is displayed")
    public boolean isProductAddedToCart() {
        return isDisplayed(successMessage);
    }

    @Step("Add product to cart")
    public ProductPage addToCart() {
        click(addToCartButton);
        return this;
    }

    @Step("Navigate to Checkout from product page")
    public CheckoutPage clickCheckout() {
        click(checkoutLink);
        return new CheckoutPage(driver);
    }
}
