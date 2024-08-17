package br.dev.diisk.infra.configurations.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.dtos.GenericResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Integer statusCode = HttpServletResponse.SC_FORBIDDEN;
        GenericResponse<Object> responseObject = GenericResponse.getErrorInstanceFor(statusCode,
                new ErrorResponse("You have no rights here."));
        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseObject));
    }

}
