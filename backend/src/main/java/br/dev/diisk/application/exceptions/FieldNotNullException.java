package br.dev.diisk.application.exceptions;

public class FieldNotNullException extends BadRequestFieldCustomRuntimeException {

    public FieldNotNullException(Class<?> classObject, String field) {
        super(classObject, "The field can't be bull.", field);
    }

}
