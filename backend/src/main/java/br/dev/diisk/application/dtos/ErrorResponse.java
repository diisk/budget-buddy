package br.dev.diisk.application.dtos;

import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    public ErrorResponse(FieldError fieldError){
        this.message = fieldError.getDefaultMessage();
        this.field = fieldError.getField();
        this.objectName = fieldError.getObjectName();
    }

    public ErrorResponse(String message){
        this.message = message;
    }

    public ErrorResponse(String field, String objectName, String message){
        this.message = message;
        this.field = field;
        this.objectName = objectName;
    }

    public ErrorResponse(String value, String message) {
        this.value = value;
        this.message = message;
    }

    private String field;
    private String objectName;
    private String value;
    private String message;
}
