package com.bruno.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product POJO for mapping JSON data.
 */
public class Product {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name;
    }
}
