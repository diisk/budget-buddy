package br.dev.diisk.infra.configurations.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.response.ErrorDetailsResponse;
import br.dev.diisk.application.interfaces.IResponseService;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
@Order(1)
public class PersistenceExceptionHandlerConfig {

    private final IResponseService responseService;

    public PersistenceExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return responseService.notFound(new ErrorDetailsResponse(ex.getMessage(), null));
    }

}
