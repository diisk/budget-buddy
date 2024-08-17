package br.dev.diisk.application.interfaces;

import java.net.URI;
import java.util.Collection;

import org.springframework.http.ResponseEntity;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.dtos.GenericResponse;

public interface IResponseService {

    public <T> ResponseEntity<GenericResponse<T>> ok(T content);

    public <T> ResponseEntity<GenericResponse<T>> ok();

    public <T> ResponseEntity<GenericResponse<T>> created(URI uri, T content);

    public <T> ResponseEntity<GenericResponse<T>> created(URI uri);

    public ResponseEntity<GenericResponse<?>> badRequest(ErrorResponse error);

    public ResponseEntity<GenericResponse<?>> badRequest(Collection<ErrorResponse> errors);

    public ResponseEntity<GenericResponse<?>> notFound(ErrorResponse error);

    public ResponseEntity<GenericResponse<?>> notFound(Collection<ErrorResponse> errors);

    public ResponseEntity<GenericResponse<?>> unauthorized(ErrorResponse error);

    public ResponseEntity<GenericResponse<?>> unauthorized(Collection<ErrorResponse> errors);

    public ResponseEntity<GenericResponse<?>> forbidden(ErrorResponse error);

    public ResponseEntity<GenericResponse<?>> forbidden(Collection<ErrorResponse> errors);

    public ResponseEntity<GenericResponse<?>> internal(ErrorResponse error);

    public ResponseEntity<GenericResponse<?>> internal(Collection<ErrorResponse> errors);

}
