package com.bruno.ecommerce.tests;

import com.bruno.ecommerce.base.DriverFactory;
import com.bruno.ecommerce.utils.TestWatcherExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseTest for all test classes.
 * Handles WebDriver setup/teardown and attaches the custom TestWatcherExtension.
 */
@ExtendWith(TestWatcherExtension.class)
public abstract class BaseTest {

    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        logger.info("Initializing WebDriver for the test case.");
        driver = DriverFactory.initDriver();
    }

    @AfterEach
    public void tearDown() {
        logger.info("Quitting WebDriver and cleaning up thread-safe instance.");
        DriverFactory.quitDriver();
    }
}
