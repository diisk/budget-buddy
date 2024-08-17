package br.dev.diisk.infra.configurations.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.exceptions.FutureDateException;
import br.dev.diisk.application.exceptions.PastDateException;
import br.dev.diisk.application.interfaces.IResponseService;

@RestControllerAdvice
public class DateExceptionHandlerConfig {

    private final IResponseService responseService;

    public DateExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<?> tratarFutureDateException(FutureDateException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

    @ExceptionHandler(PastDateException.class)
    public ResponseEntity<?> tratarPastDateException(PastDateException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

}
