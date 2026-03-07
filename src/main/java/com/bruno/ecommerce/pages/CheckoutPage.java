package com.bruno.ecommerce.pages;

import com.bruno.ecommerce.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CheckoutPage for the Ecommerce Playground.
 */
public class CheckoutPage extends BasePage {

    private final By checkoutHeader = By.xpath("//h1[text()='Checkout']");
    private final By firstNameInput = By.id("input-payment-firstname");
    private final By lastNameInput = By.id("input-payment-lastname");
    private final By addressInput = By.id("input-payment-address-1");
    private final By cityInput = By.id("input-payment-city");
    private final By postcodeInput = By.id("input-payment-postcode");
    private final By agreeCheckbox = By.xpath("//label[@for='input-agree']");
    private final By continueButton = By.id("button-save");
    private final By confirmOrderButton = By.id("button-confirm");
    private final By successMessage = By.xpath("//h1[text()='Your order has been placed!']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify if on checkout page")
    public boolean isOnCheckoutPage() {
        return isDisplayed(checkoutHeader);
    }

    @Step("Confirm the order")
    public CheckoutPage confirmOrder() {
        click(confirmOrderButton);
        return this;
    }

    @Step("Verify order success message")
    public boolean isOrderSuccessful() {
        return isDisplayed(successMessage);
    }

    @Step("Fill Billing Details")
    public CheckoutPage fillBillingDetails(String firstName, String lastName, String address, String city, String postcode) {
        // The form might already be pre-filled if logged in, but let's assume we fill it
        if (isDisplayed(firstNameInput)) {
            sendKeys(firstNameInput, firstName);
            sendKeys(lastNameInput, lastName);
            sendKeys(addressInput, address);
            sendKeys(cityInput, city);
            sendKeys(postcodeInput, postcode);
        }
        return this;
    }

    @Step("Agree to terms and click continue")
    public CheckoutPage agreeToTerms() {
        click(agreeCheckbox);
        return this;
    }

    @Step("Click continue/save button")
    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }
}
