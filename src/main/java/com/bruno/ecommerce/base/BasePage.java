package com.bruno.ecommerce.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BasePage serves as the parent class for all Page Objects.
 * It encapsulates common Selenium actions and implements resilient wait strategies.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    private static final int TIMEOUT = 15;
    private static final int POLLING = 500;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT))
                .pollingEvery(Duration.ofMillis(POLLING))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    /**
     * Injects cookies into the current browser session.
     */
    public void injectCookies(Map<String, String> cookies) {
        cookies.forEach((key, value) -> driver.manage().addCookie(new Cookie(key, value)));
        driver.navigate().refresh();
    }

    /**
     * Waits for an element to be visible and returns it.
     * Implements a simple Self-Healing (Smart Locator) logic as a fallback.
     */
    protected WebElement waitForVisibility(By locator) {
        try {
            logger.debug("Waiting for visibility of element: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.warn("Primary locator failed: {}. Attempting Self-Healing fallback...", locator);
            return attemptSelfHealing(locator);
        }
    }

    /**
     * Simple Self-Healing logic that tries to find the element by text if the primary locator fails.
     * In a real Big Tech scenario, this could involve more complex strategies or external libs.
     */
    private WebElement attemptSelfHealing(By primaryLocator) {
        String locatorString = primaryLocator.toString();
        // Extract a possible text or ID to try a fallback
        if (locatorString.contains("id: ")) {
            String id = locatorString.split("id: ")[1];
            logger.info("Self-healing: Attempting to find element by partial text match for ID: {}", id);
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@id, '" + id + "') or contains(text(), '" + id + "')]")));
            } catch (TimeoutException te) {
                logger.error("Self-healing failed for locator: {}", primaryLocator);
                throw te;
            }
        }
        throw new TimeoutException("Primary locator and Self-healing failed for: " + primaryLocator);
    }

    /**
     * Waits for an element to be clickable and returns it.
     */
    protected WebElement waitForClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Clicks on an element after waiting for it to be clickable.
     */
    protected void click(By locator) {
        logger.info("Clicking on element: {}", locator);
        waitForClickable(locator).click();
    }

    /**
     * Enters text into a field after waiting for visibility.
     */
    protected void sendKeys(By locator, String text) {
        logger.info("Entering text '{}' into element: {}", text, locator);
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the text from an element after waiting for visibility.
     */
    protected String getText(By locator) {
        String text = waitForVisibility(locator).getText();
        logger.debug("Text from element {}: {}", locator, text);
        return text;
    }

    /**
     * Verifies if an element is displayed.
     */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            logger.warn("Element not displayed within timeout: {}", locator);
            return false;
        }
    }
}
