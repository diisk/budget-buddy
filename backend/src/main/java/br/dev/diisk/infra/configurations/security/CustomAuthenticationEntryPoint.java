package br.dev.diisk.infra.configurations.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.dev.diisk.application.dtos.ErrorResponse;
import br.dev.diisk.application.dtos.GenericResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        Integer statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        GenericResponse<Object> responseObject = GenericResponse.getErrorInstanceFor(statusCode,
                new ErrorResponse("You must be logged in to do this."));
        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseObject));
    }

}
