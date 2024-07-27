package br.dev.diisk.application.exceptions;

import org.springframework.validation.FieldError;

import lombok.Getter;

@Getter
public class ValueAlreadyInDatabaseException extends RuntimeException {

    private FieldError fieldError;

    public ValueAlreadyInDatabaseException(String field, String objectName) {
        this.fieldError = new FieldError(objectName, field, "This value already exists in database.");
    }

}
