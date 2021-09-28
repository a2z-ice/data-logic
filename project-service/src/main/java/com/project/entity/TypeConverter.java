package com.project.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type,String> {
    @Override
    public String convertToDatabaseColumn(Type type) {
        return type == null ? null : type.getName();
    }

    @Override
    public Type convertToEntityAttribute(String dbName) {
        return Type.fromName(dbName);
    }
}
