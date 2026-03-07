package com.bruno.ecommerce.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;

/**
 * Singleton Manager for Testcontainers.
 * Ensures that only one container is spun up for the entire test suite execution.
 */
public class DockerGridManager {

    private static final Logger logger = LoggerFactory.getLogger(DockerGridManager.class);
    private static BrowserWebDriverContainer<?> container;

    private DockerGridManager() {
        // Private constructor
    }

    public static synchronized BrowserWebDriverContainer<?> getContainer(String browser) {
        if (container == null) {
            logger.info("Starting Docker Container for browser: {}", browser);

            // Resource is closed via ShutdownHook
            @SuppressWarnings("resource")
            BrowserWebDriverContainer<?> newContainer = new BrowserWebDriverContainer<>()
                    .withCapabilities(getCapabilities(browser))
                    .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null); // Disable VNC recording by default for speed

            container = newContainer;
            container.start();
            
            // Register shutdown hook to stop container when JVM exits
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Stopping Docker Container...");
                container.stop();
            }));
        }
        return container;
    }

    private static org.openqa.selenium.Capabilities getCapabilities(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new org.openqa.selenium.chrome.ChromeOptions();
            case "firefox":
                return new org.openqa.selenium.firefox.FirefoxOptions();
            case "edge":
                return new org.openqa.selenium.edge.EdgeOptions();
            default:
                throw new IllegalArgumentException("Unsupported browser for Docker: " + browser);
        }
    }
}
