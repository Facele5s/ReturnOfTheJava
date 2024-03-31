package edu.java.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.net.URI;

@Converter
public class UriConverter implements AttributeConverter<URI, String> {
    @Override
    public String convertToDatabaseColumn(URI uri) {
        if (uri == null) {
            return null;
        }

        return uri.toString();
    }

    @Override
    public URI convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return URI.create(s);
    }
}
