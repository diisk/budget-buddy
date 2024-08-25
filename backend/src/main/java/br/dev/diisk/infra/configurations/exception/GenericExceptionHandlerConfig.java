package br.dev.diisk.infra.configurations.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.dev.diisk.application.dtos.response.ErrorDetailsResponse;
import br.dev.diisk.application.dtos.response.FieldErrorDetailsResponse;
import br.dev.diisk.application.dtos.response.ValueErrorDetailsResponse;
import br.dev.diisk.application.exceptions.BadRequestFieldCustomRuntimeException;
import br.dev.diisk.application.exceptions.BadRequestValueCustomRuntimeException;
import br.dev.diisk.application.exceptions.CustomRuntimeException;
import br.dev.diisk.application.exceptions.NotFoundFieldCustomRuntimeException;
import br.dev.diisk.application.exceptions.NotFoundValueCustomRuntimeException;
import br.dev.diisk.application.interfaces.IResponseService;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@Order(2)
@RequiredArgsConstructor
public class GenericExceptionHandlerConfig {

    private final IResponseService responseService;
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return responseService.internal(new ErrorDetailsResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleException(AuthorizationDeniedException ex) {
        return responseService.unauthorized(new ErrorDetailsResponse("You have no rights here.", null));
    }
    
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<?> handleCustomRuntimeException(CustomRuntimeException ex) {
        System.err.println(ex);
        return responseService.internal(new ErrorDetailsResponse("Exception expected but has no handler.", null));
    }

    @ExceptionHandler(BadRequestFieldCustomRuntimeException.class)
    public ResponseEntity<?> handleBadRequestFieldCustomRuntimeException(BadRequestFieldCustomRuntimeException ex) {
        return responseService.badRequest(new FieldErrorDetailsResponse(ex));
    }

    @ExceptionHandler(BadRequestValueCustomRuntimeException.class)
    public ResponseEntity<?> handleBadRequestValueCustomRuntimeException(BadRequestValueCustomRuntimeException ex) {
        return responseService.badRequest(new ValueErrorDetailsResponse(ex));
    }

    @ExceptionHandler(NotFoundFieldCustomRuntimeException.class)
    public ResponseEntity<?> handleNotFoundFieldCustomRuntimeException(NotFoundFieldCustomRuntimeException ex) {
        return responseService.notFound(new FieldErrorDetailsResponse(ex));
    }

    @ExceptionHandler(NotFoundValueCustomRuntimeException.class)
    public ResponseEntity<?> handleNotFoundValueCustomRuntimeException(NotFoundValueCustomRuntimeException ex) {
        return responseService.notFound(new ValueErrorDetailsResponse(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return responseService.badRequest("The following method arguments aren't valids.",
                ex.getFieldErrors().stream().map(FieldErrorDetailsResponse::new).toList());
    }

}
