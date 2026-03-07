package com.bruno.ecommerce.base;

import com.bruno.ecommerce.config.ConfigurationManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
        // Private constructor to prevent instantiation of utility class
    }

    public static WebDriver initDriver() {
        String browser = ConfigurationManager.getConfiguration().browser();
        String target = ConfigurationManager.getConfiguration().target();
        boolean headless = ConfigurationManager.getConfiguration().headless();

        logger.info("Initializing WebDriver - Browser: {}, Target: {}, Headless: {}", browser, target, headless);

        WebDriver driver;
        
        switch (target.toLowerCase()) {
            case "remote":
                driver = initRemoteDriver(browser, headless);
                break;
            case "docker":
                driver = initDockerDriver();
                break;
            default:
                driver = initLocalDriver(browser, headless);
                break;
        }

        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
        return driver;
    }

    private static WebDriver initDockerDriver() {
        return DockerGridManager.getContainer(ConfigurationManager.getConfiguration().browser()).getWebDriver();
    }

    private static WebDriver initLocalDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--remote-allow-origins=*");
                return new ChromeDriver(chromeOptions);
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                return new EdgeDriver(edgeOptions);
            default:
                throw new RuntimeException("Unsupported local browser: " + browser);
        }
    }

    private static WebDriver initRemoteDriver(String browser, boolean headless) {
        String gridUrl = String.format("http://%s:%s/wd/hub", 
                ConfigurationManager.getConfiguration().gridUrl(), 
                ConfigurationManager.getConfiguration().gridPort());
        
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) chromeOptions.addArguments("--headless=new");
                    return new RemoteWebDriver(URI.create(gridUrl).toURL(), chromeOptions);
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) firefoxOptions.addArguments("-headless");
                    return new RemoteWebDriver(URI.create(gridUrl).toURL(), firefoxOptions);
                default:
                    throw new RuntimeException("Unsupported remote browser: " + browser);
            }
        } catch (MalformedURLException e) {
            logger.error("Grid URL is invalid: {}", gridUrl);
            throw new RuntimeException("Failed to initialize Remote WebDriver", e);
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
        }
    }
}
