package com.bruno.ecommerce.tests;

import com.bruno.ecommerce.pages.HomePage;
import com.bruno.ecommerce.pages.RegisterPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("E-Commerce Registration Scenarios")
@Feature("User Registration")
public class RegistrationTest extends BaseTest {

    @Test
    @Tag("Regression")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify successful user registration")
    @Story("New User Registration")
    public void testSuccessfulRegistration() {
        String uniqueEmail = "test_" + UUID.randomUUID().toString() + "@example.com";
        String uniquePhone = String.valueOf(System.currentTimeMillis());

        RegisterPage registerPage = new HomePage(driver)
                .open()
                .navigateToRegister()
                .fillRegistrationForm(
                        "Test",
                        "User",
                        uniqueEmail,
                        uniquePhone,
                        "Password123!",
                        false
                );
        
        registerPage.submit();

        assertThat(registerPage.isRegistrationSuccessful())
                .withFailMessage("Registration success message not displayed")
                .isTrue();
    }

    @Test
    @Tag("Negative")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verify registration with duplicate email")
    @Story("Duplicate Registration")
    public void testDuplicateEmailRegistration() {
        // Load an existing user from JSON (assuming the first user is already registered or we use a fixed email)
        // For a real test, we should ideally register a user first to guarantee duplication, 
        // or rely on a known existing user. Let's register one first to be safe and independent.
        
        String email = "duplicate_" + UUID.randomUUID() + "@example.com";
        String phone = String.valueOf(System.currentTimeMillis());

        // 1. Register the user for the first time
        new HomePage(driver)
                .open()
                .navigateToRegister()
                .fillRegistrationForm("First", "User", email, phone, "Pass123", false)
                .submit();
        
        // Logout to clear session
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        // 2. Try to register again with the same email
        RegisterPage registerPage = new HomePage(driver)
                .open()
                .navigateToRegister()
                .fillRegistrationForm("Second", "User", email, phone, "Pass123", false);
        
        registerPage.submit();

        String warning = registerPage.getWarningMessage();
        
        assertThat(warning)
                .withFailMessage("Expected duplicate email warning not found")
                .contains("Warning: E-Mail Address is already registered!");
    }
}
