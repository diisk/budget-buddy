package br.dev.diisk.application.exceptions;

import org.springframework.validation.FieldError;
import lombok.Getter;

@Getter
public class DbValueNotFoundException extends RuntimeException {

    private FieldError fieldError;

    public DbValueNotFoundException(String field, String objectName) {
        this.fieldError = new FieldError(objectName, field, "The specified value was not found in database.");
    }

}
