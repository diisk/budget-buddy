package br.dev.diisk.infra.configurations.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.exceptions.InvalidUserException;
import br.dev.diisk.application.interfaces.IResponseService;

@RestControllerAdvice
public class AuthenticationExceptionHandlerConfig {

    private final IResponseService responseService;

    public AuthenticationExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> tratarValueAlreadyInDatabaseException(InvalidUserException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getMessage()));
    }

}
