package br.dev.diisk.application.exceptions;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Invalid username or password.");
    }

}
