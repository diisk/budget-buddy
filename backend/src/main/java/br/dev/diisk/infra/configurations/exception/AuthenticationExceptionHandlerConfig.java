package br.dev.diisk.infra.configurations.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.response.ErrorDetailsResponse;
import br.dev.diisk.application.exceptions.authentication.InvalidUserException;
import br.dev.diisk.application.interfaces.IResponseService;

@RestControllerAdvice
@Order(1)
public class AuthenticationExceptionHandlerConfig {

    private final IResponseService responseService;

    public AuthenticationExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> handleInvalidUserException(InvalidUserException ex) {
        return responseService.badRequest(new ErrorDetailsResponse(ex.getMessage(), null));
    }

}
