package com.example.demo.utils.exception;

public enum ExceptionType {

    ENTITY_NOT_FOUND("Entity Not Found"),
    DUPLICATE_ENTITY("Duplicate Entity Found"),
    VALIDATION_ERROR("Validation Error");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
