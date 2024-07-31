package br.dev.diisk.infra.services;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.interfaces.IResponseService;

@Service
public class ResponseService implements IResponseService {

    @Override
    public <T> ResponseEntity<GenericResponse<T>> ok(T content) {
        return genericResponse(content, HttpStatus.OK, true);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> ok() {
        return ok(null);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> created(URI uri, T content) {
        GenericResponse<T> ret = new GenericResponse<T>();
        ret.setStatusCode(HttpStatus.CREATED.value());
        ret.setContent(content);
        return ResponseEntity.created(uri).body(ret);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> created(URI uri) {
        return created(uri, null);
    }

    private <T> ResponseEntity<GenericResponse<T>> error(HttpStatus httpStatus, T content) {
        return genericResponse(content, httpStatus, false);
    }

    private <T> ResponseEntity<GenericResponse<T>> genericResponse(T content, HttpStatus httpStatus,
            Boolean success) {
        GenericResponse<T> ret = new GenericResponse<T>();
        ret.setContent(content);
        ret.setStatusCode(httpStatus.value());
        ret.setSuccess(success);
        return ResponseEntity.status(httpStatus).body(ret);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> badRequest(T content) {
        return error(HttpStatus.BAD_REQUEST, content);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> badRequest() {
        return badRequest(null);
    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> notFound(T content) {
        return error(HttpStatus.NOT_FOUND, content);

    }

    @Override
    public <T> ResponseEntity<GenericResponse<T>> notFound() {
        return notFound(null);
    }

}
