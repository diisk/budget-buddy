package br.dev.diisk.infra.configurations.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.IResponseService;

@RestControllerAdvice
public class DatabaseExceptionHandlerConfig {

    private final IResponseService responseService;

    public DatabaseExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(ValueAlreadyInDatabaseException.class)
    public ResponseEntity<?> tratarValueAlreadyInDatabaseException(ValueAlreadyInDatabaseException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(DbValueNotFoundException.class)
    public ResponseEntity<?> tratarDbValueNotFoundException(DbValueNotFoundException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

}
