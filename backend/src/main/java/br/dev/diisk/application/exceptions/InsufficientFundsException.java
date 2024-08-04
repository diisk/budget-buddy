package br.dev.diisk.application.exceptions;

import org.springframework.validation.FieldError;
import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {

    private FieldError fieldError;

    public InsufficientFundsException(String field, String objectName) {
        this.fieldError = new FieldError(objectName, field, "Insufficient funds.");
    }

}
