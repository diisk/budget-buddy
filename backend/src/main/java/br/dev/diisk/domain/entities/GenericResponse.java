package br.dev.diisk.domain.entities;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

    @Getter(value = AccessLevel.NONE)
    private Boolean success = true;
    private Integer statusCode;
    @Setter(value = AccessLevel.NONE)
    private String date;
    private T content;

    public GenericResponse(){
        date = LocalDateTime.now().toString();
    }

    public Boolean isSuccess() {
        return success;
    }

}
