package br.dev.diisk.application.exceptions.authentication;

import br.dev.diisk.application.exceptions.CustomRuntimeException;
import lombok.Getter;

@Getter
public class InvalidUserException extends CustomRuntimeException {

    public InvalidUserException() {
        super(null, "Invalid username or password.");
    }

}
