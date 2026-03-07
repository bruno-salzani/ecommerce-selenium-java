package com.bruno.ecommerce.api;

import com.bruno.ecommerce.config.ConfigurationManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;

/**
 * Client for API Seeding. Used to bypass UI for pre-conditions like login and cart management.
 */
public class ApiClient {

    public ApiClient() {
        RestAssured.baseURI = ConfigurationManager.getConfiguration().baseUrl();
    }

    /**
     * Performs a login via API and returns the session cookies.
     */
    public Map<String, String> login(String email, String password) {
        Response response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("email", email)
                .formParam("password", password)
                .post("index.php?route=account/login");

        return response.getCookies();
    }

    /**
     * Adds a product to the cart via API.
     */
    public void addProductToCart(int productId, int quantity, Map<String, String> cookies) {
        RestAssured.given()
                .cookies(cookies)
                .formParam("product_id", productId)
                .formParam("quantity", quantity)
                .post("index.php?route=checkout/cart/add");
    }
}
