package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {

    private final By cartItems = By.xpath("//div[@class='table-responsive']//tbody/tr");
    private final By quantityInput = By.xpath("//div[@class='input-group btn-block']//input");
    private final By updateButton = By.xpath("//button[@data-original-title='Update']");
    private final By removeButton = By.xpath("//button[@data-original-title='Remove']");
    private final By successAlert = By.cssSelector(".alert-success");
    private final By emptyCartMessage = By.xpath("//div[@id='content']//p[contains(text(),'Your shopping cart is empty!')]");
    private final By checkoutButton = By.xpath("//a[text()='Checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get number of items in cart")
    public int getNumberOfItems() {
        return driver.findElements(cartItems).size();
    }

    @Step("Update quantity of first item to {quantity}")
    public CartPage updateQuantity(String quantity) {
        WebElement input = waitForVisibility(quantityInput);
        input.clear();
        input.sendKeys(quantity);
        click(updateButton);
        waitForVisibility(successAlert); // Wait for success message
        return this;
    }

    @Step("Remove first item from cart")
    public CartPage removeItem() {
        click(removeButton);
        // After removal, either success message or empty cart message appears
        return this;
    }

    @Step("Verify success message is displayed")
    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successAlert);
    }

    @Step("Verify cart is empty")
    public boolean isCartEmpty() {
        return isDisplayed(emptyCartMessage);
    }

    @Step("Proceed to Checkout")
    public CheckoutPage checkout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
}
