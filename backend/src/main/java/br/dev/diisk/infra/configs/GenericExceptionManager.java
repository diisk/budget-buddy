package br.dev.diisk.infra.configs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.FutureDateException;
import br.dev.diisk.application.exceptions.InsufficientFundsException;
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
    public ResponseEntity<?> tratarEntityNotFound() {
        return responseService.notFound();
    }

    @ExceptionHandler(ValueAlreadyInDatabaseException.class)
    public ResponseEntity<?> tratarValueAlreadyInDatabaseException(ValueAlreadyInDatabaseException ex) {
        return responseService.badRequest(new FieldErrorData(ex.getFieldError()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> tratarInsufficientFundsException(InsufficientFundsException ex) {
        return responseService.badRequest(new FieldErrorData(ex.getFieldError()));
    }

    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<?> tratarFutureDateException(FutureDateException ex) {
        return responseService.badRequest(new FieldErrorData(ex.getFieldError()));
    }

    @ExceptionHandler(DbValueNotFoundException.class)
    public ResponseEntity<?> tratarDbValueNotFoundException(DbValueNotFoundException ex) {
        return responseService.badRequest(new FieldErrorData(ex.getFieldError()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return responseService.badRequest(erros.stream().map(FieldErrorData::new).toList());
    }

    private record FieldErrorData(String field, String object, String message) {
        public FieldErrorData(FieldError error) {
            this(error.getField(), error.getObjectName(), error.getDefaultMessage());
        }
    }

}
