package br.dev.diisk.infra.configurations.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.interfaces.IResponseService;

@RestControllerAdvice
public class GenericExceptionHandlerConfig {

    private final IResponseService responseService;

    public GenericExceptionHandlerConfig(IResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> tratarEntityNotFound(Exception ex) {
        return responseService.notFound(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        return responseService.badRequest(ex.getFieldErrors().stream().map(ErrorResponse::new).toList());
    }

}
