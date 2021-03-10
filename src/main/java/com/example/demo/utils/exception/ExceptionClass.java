package com.example.demo.utils.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ExceptionClass implements Serializable {

    private ExceptionType exceptionType;

    private EntityType entityType;

    private String message;

    public ExceptionClass(ExceptionType exceptionType, EntityType entityType, String message) {
        this.entityType = entityType;
        this.exceptionType = exceptionType;
        this.message = message;
    }

    public static String toJSONString(ExceptionType exceptionType, EntityType entityType, String message) throws JsonProcessingException {
        ExceptionClass exceptionClass = new ExceptionClass(exceptionType, entityType, message);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(exceptionClass);
    }

}
