package br.dev.diisk.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

    private Boolean success;
    private Integer statusCode;
    @Setter(value = AccessLevel.NONE)
    private String date;
    private Collection<ErrorResponse> errors;
    private T content;

    public GenericResponse(Integer statusCode, Collection<ErrorResponse> errors) {
        this.statusCode = statusCode;
        this.errors = errors;
        this.success = false;
        this.date = LocalDateTime.now().toString();
    }

    public GenericResponse(T content, Integer statusCode) {
        this.statusCode = statusCode;
        this.content = content;
        this.success = true;
        this.date = LocalDateTime.now().toString();
    }

    public static GenericResponse<Object> getErrorInstanceFor(Integer statusCode, ErrorResponse error) {
        Collection<ErrorResponse> errorList = new ArrayList<>();
        errorList.add(error);
        return new GenericResponse<>(statusCode, errorList);
    }

    public static GenericResponse<Object> getErrorInstanceFor(Integer statusCode, ErrorResponse[] errors) {
        Collection<ErrorResponse> errorList = new ArrayList<>();
        for (ErrorResponse error : errors) {
            errorList.add(error);
        }
        return new GenericResponse<>(statusCode, errorList);
    }

    public Boolean isSuccess() {
        return success;
    }

}
