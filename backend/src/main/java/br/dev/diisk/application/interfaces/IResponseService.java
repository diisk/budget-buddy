package br.dev.diisk.application.interfaces;

import java.net.URI;
import org.springframework.http.ResponseEntity;

import br.dev.diisk.application.dtos.GenericResponse;

public interface IResponseService {

    public <T> ResponseEntity<GenericResponse<T>> ok(T content);

    public <T> ResponseEntity<GenericResponse<T>> ok();

    public <T> ResponseEntity<GenericResponse<T>> created(URI uri, T content);

    public <T> ResponseEntity<GenericResponse<T>> created(URI uri);

    public <T> ResponseEntity<GenericResponse<T>> badRequest(T content);

    public <T> ResponseEntity<GenericResponse<T>> badRequest();

    public <T> ResponseEntity<GenericResponse<T>> notFound(T content);

    public <T> ResponseEntity<GenericResponse<T>> notFound();

}
