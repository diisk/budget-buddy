package br.dev.diisk.application.exceptions.domain;

import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import lombok.Getter;

@Getter
public class InsufficientFundsException extends BadRequestFieldCustomRuntimeException {

    public InsufficientFundsException(Class<?> classObject, String field) {
        super(classObject, "Insufficient funds.", field);
    }

}
