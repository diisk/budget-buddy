package br.dev.diisk.infra.services;

import java.net.URI;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.interfaces.IResponseService;

@Service
public class ResponseService implements IResponseService {

    @Override
    public <T> ResponseEntity<GenericResponse<T>> ok(T content) {
        return successResponse(content, HttpStatus.OK);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> ok() {
        return ok(null);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> created(URI uri, T content) {
        return ResponseEntity.created(uri).body(new GenericResponse<T>(content, HttpStatus.CREATED.value()));
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> created(URI uri) {
        return created(uri, null);
    }

    private <T> ResponseEntity<GenericResponse<T>> successResponse(T content, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(
                new GenericResponse<T>(content, httpStatus.value()));
    }

    private ResponseEntity<GenericResponse<?>> errorResponse(Collection<ErrorResponse> errors, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new GenericResponse<Object>(httpStatus.value(), errors));
    }

    private ResponseEntity<GenericResponse<?>> errorResponse(ErrorResponse error, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(GenericResponse.getErrorInstanceFor(httpStatus.value(), error));
    }

    @Override
    public ResponseEntity<GenericResponse<?>> badRequest(ErrorResponse error) {
        return errorResponse(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> badRequest(Collection<ErrorResponse> errors) {
        return errorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> notFound(ErrorResponse error) {
        return errorResponse(error, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> notFound(Collection<ErrorResponse> errors) {
        return errorResponse(errors, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> unauthorized(ErrorResponse error) {
        return errorResponse(error, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> unauthorized(Collection<ErrorResponse> errors) {
        return errorResponse(errors, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> forbidden(ErrorResponse error) {
        return errorResponse(error, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> forbidden(Collection<ErrorResponse> errors) {
        return errorResponse(errors, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> internal(ErrorResponse error) {
        return errorResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<GenericResponse<?>> internal(Collection<ErrorResponse> errors) {
        return errorResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
