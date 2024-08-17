package br.dev.diisk.infra.configurations.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.interfaces.IResponseService;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class JakartaExceptionHandlerConfig {

    private final IResponseService responseService;

    public JakartaExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarEntityNotFound(EntityNotFoundException ex) {
        return responseService.notFound(new ErrorResponse(ex.getMessage()));
    }

}
