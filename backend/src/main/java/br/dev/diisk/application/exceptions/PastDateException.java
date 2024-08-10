package br.dev.diisk.application.exceptions;

import java.time.LocalDateTime;

import org.springframework.validation.FieldError;
import lombok.Getter;

@Getter
public class PastDateException extends RuntimeException {

    private FieldError fieldError;

    public PastDateException(String field) {
        this.fieldError = new FieldError(LocalDateTime.class.getSimpleName(), field, "The specified datetime is before the current datetime.");
    }

}
