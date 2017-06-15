package com.arthur.webnovel.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // thread-safe

    public static <t> Map<String, t> toMap(final String str) {
        try {
            TypeReference<Map<String, t>> typeRef = new TypeReference<Map<String, t>>() {
            };
            Map<String, t> map = objectMapper.readValue(str, typeRef);
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <t> List<t> toList(final String str) {
        try {
            TypeReference<List<t>> typeRef = new TypeReference<List<t>>() {
            };
            List<t> list = objectMapper.readValue(str, typeRef);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(final Object object) {
        try {
            String str = objectMapper.writeValueAsString(object);
            return str;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
