package br.dev.diisk.application.exceptions.date;

import java.time.LocalDateTime;

import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import lombok.Getter;

@Getter
public class PastDateException extends BadRequestFieldCustomRuntimeException {

    public PastDateException(String field) {
        super(LocalDateTime.class, "The specified datetime is before the current datetime.", field);
    }

}
