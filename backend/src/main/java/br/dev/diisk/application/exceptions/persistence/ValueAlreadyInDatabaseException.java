package br.dev.diisk.application.exceptions.persistence;

import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import lombok.Getter;

@Getter
public class ValueAlreadyInDatabaseException extends BadRequestFieldCustomRuntimeException {

    public ValueAlreadyInDatabaseException(Class<?> classObject, String field) {
        super(classObject, "The value already exists in database.", field);
    }

}
