package br.dev.diisk.application.exceptions;

import java.time.LocalDateTime;

import org.springframework.validation.FieldError;
import lombok.Getter;

@Getter
public class FutureDateException extends RuntimeException {

    private FieldError fieldError;

    public FutureDateException(String field) {
        this.fieldError = new FieldError(LocalDateTime.class.getSimpleName(), field, "The specified datetime is after the actual datetime.");
    }

}
