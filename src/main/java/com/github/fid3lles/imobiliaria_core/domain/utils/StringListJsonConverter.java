package com.github.fid3lles.imobiliaria_core.domain.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Converter
public class StringListJsonConverter implements AttributeConverter<List<String>, String> {

    private static final tools.jackson.databind.ObjectMapper om = new ObjectMapper();
    private static final tools.jackson.core.type.TypeReference<List<String>> LIST = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return attribute == null ? null : om.writeValueAsString(attribute); // ["a","b"]
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao serializar List<String> para JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null : om.readValue(dbData, LIST);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao desserializar JSON para List<String>", e);
        }
    }
}