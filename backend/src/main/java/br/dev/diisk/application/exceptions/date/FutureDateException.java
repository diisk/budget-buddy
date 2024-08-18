package br.dev.diisk.application.exceptions.date;

import java.time.LocalDateTime;

import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import lombok.Getter;

@Getter
public class FutureDateException extends BadRequestFieldCustomRuntimeException {

    public FutureDateException(String field) {
        super(LocalDateTime.class, "The specified datetime is after the current datetime.", field);
    }

}
