package br.dev.diisk.application.exceptions.domain;

import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import lombok.Getter;

@Getter
public class FundStorageMustBeEmptyException extends BadRequestFieldCustomRuntimeException {

    public FundStorageMustBeEmptyException(Class<?> classObject, String field) {
        super(classObject, "The fund storage balance must be 0.", field);
    }

}
