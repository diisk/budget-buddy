package br.dev.diisk.application.exceptions.domain;

import br.dev.diisk.application.exceptions.BadRequestValueCustomRuntimeException;

public class InvalidTransactionTypeException extends BadRequestValueCustomRuntimeException {

    public InvalidTransactionTypeException(Class<?> classObject, String value) {
        super(classObject, "Invalid transaction type value.", value);
    }

}
