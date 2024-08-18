package br.dev.diisk.infra.configurations.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.response.ErrorResponse;
import br.dev.diisk.application.interfaces.IResponseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final IResponseService responseService;

    public CustomAuthenticationEntryPoint(IResponseService responseService) {
        this.responseService = responseService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        Integer statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        ErrorResponse responseObject = ErrorResponse.getErrorInstance(statusCode, "You must be logged in to do this.");
        responseService.writeResponseObject(response, statusCode, responseObject);
    }

}
