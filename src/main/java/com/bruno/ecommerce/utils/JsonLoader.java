package com.bruno.ecommerce.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility to load test data from JSON files.
 */
public class JsonLoader {

    private static final Logger logger = LoggerFactory.getLogger(JsonLoader.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Loads a list of objects from a JSON file.
     * @param filePath Path to the JSON file
     * @param typeReference Jackson TypeReference for the list
     * @param <T> Type of object
     * @return List of objects
     */
    public static <T> List<T> loadList(String filePath, TypeReference<List<T>> typeReference) {
        try {
            logger.info("Loading JSON data from: {}", filePath);
            return mapper.readValue(new File(filePath), typeReference);
        } catch (IOException e) {
            logger.error("Failed to load JSON data from: {}", filePath, e);
            throw new RuntimeException("Could not load test data from JSON", e);
        }
    }

    /**
     * Loads a single object from a JSON file.
     * @param filePath Path to the JSON file
     * @param clazz Class of the object
     * @param <T> Type of object
     * @return The object
     */
    public static <T> T loadObject(String filePath, Class<T> clazz) {
        try {
            logger.info("Loading JSON data from: {}", filePath);
            return mapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            logger.error("Failed to load JSON data from: {}", filePath, e);
            throw new RuntimeException("Could not load test data from JSON", e);
        }
    }
}
