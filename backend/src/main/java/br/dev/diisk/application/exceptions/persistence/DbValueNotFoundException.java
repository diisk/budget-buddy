package br.dev.diisk.application.exceptions.persistence;

import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import lombok.Getter;

@Getter
public class DbValueNotFoundException extends BadRequestFieldCustomRuntimeException {

    public DbValueNotFoundException(Class<?> classObject, String field) {
        super(classObject, "The specified value was not found in database.", field);
    }

}
