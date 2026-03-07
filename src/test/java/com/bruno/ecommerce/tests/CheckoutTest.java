package com.bruno.ecommerce.tests;

import com.bruno.ecommerce.api.ApiClient;
import com.bruno.ecommerce.models.Product;
import com.bruno.ecommerce.models.User;
import com.bruno.ecommerce.pages.HomePage;
import com.bruno.ecommerce.utils.JsonLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * E2E Suite for Checkout flow on Ecommerce Playground.
 */
@Epic("E-Commerce Checkout Flow")
@Feature("End-to-End Test")
public class CheckoutTest extends BaseTest {

    private static final String USERS_JSON = "src/test/resources/data/users.json";
    private static final String PRODUCTS_JSON = "src/test/resources/data/products.json";

    /**
     * Provider method for products data.
     */
    static Stream<Product> productProvider() {
        List<Product> products = JsonLoader.loadList(PRODUCTS_JSON, new TypeReference<List<Product>>() {});
        return products.stream();
    }

    @Test
    @Tag("Smoke")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Verify User can Login successfully")
    @Story("User Authentication")
    public void testUserLogin() {
        User user = JsonLoader.loadList(USERS_JSON, new TypeReference<List<User>>() {}).get(0);

        boolean isLoggedIn = new HomePage(driver)
                .open()
                .navigateToLogin()
                .login(user.getEmail(), user.getPassword())
                .isUserLoggedIn();

        assertThat(isLoggedIn)
                .withFailMessage("User failed to login with credentials: " + user.getEmail())
                .isTrue();
    }

    @ParameterizedTest(name = "E2E Checkout flow for product: {0}")
    @MethodSource("productProvider")
    @Tag("E2E")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Complete E2E Purchase Flow")
    @Story("Purchase Flow")
    public void testCompletePurchaseFlow(Product product) {
        boolean onCheckoutPage = new HomePage(driver)
                .open()
                .searchComponent().search(product.getName())
                .clickFirstProduct()
                .addToCart()
                .clickCheckout()
                .isOnCheckoutPage();

        assertThat(onCheckoutPage)
                .withFailMessage("Failed to reach Checkout Page for product: " + product.getName())
                .isTrue();
    }

    @Test
    @Tag("Hybrid")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Hybrid Checkout Flow (API Seeding + UI)")
    @Story("Purchase Flow")
    public void testHybridPurchaseFlow() {
        User user = JsonLoader.loadList(USERS_JSON, new TypeReference<List<User>>() {}).get(0);
        Product product = JsonLoader.loadList(PRODUCTS_JSON, new TypeReference<List<Product>>() {}).get(0);

        // API Seeding: Login and Add to Cart via API
        ApiClient apiClient = new ApiClient();
        Map<String, String> cookies = apiClient.login(user.getEmail(), user.getPassword());
        apiClient.addProductToCart(product.getId(), 1, cookies);

        // UI Execution: Start directly from Checkout Page
        new HomePage(driver)
                .open()
                .injectSessionCookies(cookies); // Inject session cookies

        // Since cookies are injected and page refreshed, user is logged in and cart has items
        // We can go directly to checkout or confirm order
        boolean onCheckoutPage = new HomePage(driver)
                .searchComponent().search(product.getName()) // For example, navigating via UI to confirm
                .clickFirstProduct()
                .clickCheckout()
                .isOnCheckoutPage();

        assertThat(onCheckoutPage)
                .withFailMessage("Failed to reach Checkout Page using Hybrid Seeding")
                .isTrue();
    }
}
