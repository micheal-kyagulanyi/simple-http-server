package net.eziedev.httpserver.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class Json {

    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws IOException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object obj) throws IOException {
        return myObjectMapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws IOException {
        return generateJson(node, false);
    }

    public static String stringifyPretty(JsonNode node) throws IOException {
        return generateJson(node, true);
    }

    private static String generateJson(Object obj, boolean pretty) throws IOException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(obj);
    }
}
