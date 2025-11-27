package com.exchange.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// JsonFileUtil - JSON file read/write utilities
public class JsonFileUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    
    // Read a single JSON object from file
    public static <T> T readJsonFile(String path, Class<T> type) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            return objectMapper.readValue(file, type);
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + path, e);
        }
    }
    
    // Write a single JSON object to file
    public static void writeJsonFile(String path, Object data) {
        try {
            File file = new File(path);
            file.getParentFile().mkdirs(); // Create parent directories if needed
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON file: " + path, e);
        }
    }
    
    // Read JSON array from file
    public static <T> List<T> readJsonArray(String path, Class<T> type) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, 
                objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON array: " + path, e);
        }
    }
    
    // Write JSON array to file
    public static void writeJsonArray(String path, List<?> list) {
        try {
            File file = new File(path);
            file.getParentFile().mkdirs(); // Create parent directories if needed
            objectMapper.writeValue(file, list);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON array: " + path, e);
        }
    }
    
    // Read JSON with TypeReference (for complex types like Map)
    public static <T> T readJsonWithType(String path, TypeReference<T> typeRef) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return null;
            }
            return objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + path, e);
        }
    }
}
