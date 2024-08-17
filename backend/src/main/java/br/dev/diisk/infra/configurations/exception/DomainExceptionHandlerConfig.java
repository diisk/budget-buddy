package br.dev.diisk.infra.configurations.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.exceptions.InsufficientFundsException;
import br.dev.diisk.application.interfaces.IResponseService;

@RestControllerAdvice
public class DomainExceptionHandlerConfig {

    private final IResponseService responseService;

    public DomainExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> tratarInsufficientFundsException(InsufficientFundsException ex) {
        return responseService.badRequest(new ErrorResponse(ex.getFieldError()));
    }

}
