package br.dev.diisk.infra.configs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.FutureDateException;
import br.dev.diisk.application.exceptions.InsufficientFundsException;
import br.dev.diisk.application.exceptions.PastDateException;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.IResponseService;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GenericExceptionManager {

    private IResponseService responseService;

    public GenericExceptionManager(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarEntityNotFound(EntityNotFoundException ex) {
        return responseService.notFound(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ValueAlreadyInDatabaseException.class)
    public ResponseEntity<?> tratarValueAlreadyInDatabaseException(ValueAlreadyInDatabaseException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> tratarInsufficientFundsException(InsufficientFundsException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<?> tratarFutureDateException(FutureDateException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(PastDateException.class)
    public ResponseEntity<?> tratarPastDateException(PastDateException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(DbValueNotFoundException.class)
    public ResponseEntity<?> tratarDbValueNotFoundException(DbValueNotFoundException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        return responseService.badRequest(ex.getFieldErrors().stream().map(ErrorResponse::new).toList());
    }

}
