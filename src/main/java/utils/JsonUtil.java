package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import models.GameConfig;
import models.GameResult;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static GameConfig loadConfig(String configFile) {
        try {
            return objectMapper.readValue(new File(configFile), GameConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de configuración: " + e.getMessage(), e);
        }
    }

    public static String convertToJson(GameResult result) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir el resultado a JSON: " + e.getMessage(), e);
        }
    }
}