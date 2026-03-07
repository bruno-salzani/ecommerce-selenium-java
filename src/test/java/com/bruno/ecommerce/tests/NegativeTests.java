package com.bruno.ecommerce.tests;

import com.bruno.ecommerce.pages.HomePage;
import com.bruno.ecommerce.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("E-Commerce Negative Scenarios")
@Feature("Authentication and Search")
public class NegativeTests extends BaseTest {

    @Test
    @Tag("Negative")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verify error message for invalid login")
    @Story("User Authentication")
    public void testInvalidLogin() {
        LoginPage loginPage = new HomePage(driver)
                .open()
                .navigateToLogin()
                .fillEmail("invalid_user@mail.com")
                .fillPassword("wrong_password");
        
        loginPage.clickLogin();
        
        String alertMessage = loginPage.getAlertMessage();
        
        assertThat(alertMessage)
                .withFailMessage("Expected alert message not found for invalid login")
                .contains("Warning: No match for E-Mail Address and/or Password.");
    }

    @Test
    @Tag("Negative")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verify search with no results")
    @Story("Search Functionality")
    public void testSearchNoResults() {
        boolean isNoResultsMessageDisplayed = new HomePage(driver)
                .open()
                .searchComponent().search("NonExistentProductXYZ")
                .isNoResultsMessageDisplayed();

        assertThat(isNoResultsMessageDisplayed)
                .withFailMessage("No results message should be displayed for non-existent product")
                .isTrue();
    }
}
