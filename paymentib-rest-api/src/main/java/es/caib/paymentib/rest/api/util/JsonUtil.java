package es.caib.paymentib.rest.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public final class JsonUtil {

    private JsonUtil() {
        super();
    }

    public static Object fromJson(final String json, Class clase)
            throws JsonException {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Object obj = objectMapper.readValue(json, clase);
            return obj;
        } catch (final IOException e) {
            throw new JsonException(e);
        }
    }

    public static String toJson(final Object o) throws JsonException {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            return objectMapper.writeValueAsString(o);
        } catch (final JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
}
