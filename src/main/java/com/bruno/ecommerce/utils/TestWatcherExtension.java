package com.bruno.ecommerce.utils;

import com.bruno.ecommerce.base.DriverFactory;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * Custom JUnit 5 TestWatcher to handle test lifecycle events.
 * Specifically handles test failure by capturing screenshots and attaching to Allure reports.
 */
public class TestWatcherExtension implements TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(TestWatcherExtension.class);

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("Test Aborted: {} - Reason: {}", context.getDisplayName(), cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.info("Test Disabled: {} - Reason: {}", context.getDisplayName(), reason.orElse("No reason provided"));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("Test Failed: {} - Exception: {}", context.getDisplayName(), cause.getMessage());
        captureScreenshot(context.getDisplayName());
        attachBrowserLogs();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("Test Successful: {}", context.getDisplayName());
    }

    /**
     * Captures browser console logs and attaches them to the Allure report.
     */
    private void attachBrowserLogs() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            StringBuilder logs = new StringBuilder();
            for (LogEntry entry : logEntries) {
                logs.append(entry.getLevel()).append(": ").append(entry.getMessage()).append("\n");
            }
            if (logs.length() > 0) {
                Allure.addAttachment("Browser Console Logs", "text/plain", logs.toString());
                logger.info("Browser console logs attached to Allure.");
            }
        }
    }

    /**
     * Captures a screenshot and attaches it to the Allure report.
     */
    private void captureScreenshot(String testName) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on Failure: " + testName, new ByteArrayInputStream(screenshot));
            logger.info("Screenshot captured and attached to Allure for failed test: {}", testName);
        } else {
            logger.error("WebDriver instance is null. Cannot capture screenshot for failed test: {}", testName);
        }
    }
}
