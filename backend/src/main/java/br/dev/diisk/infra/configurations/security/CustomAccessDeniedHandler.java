package br.dev.diisk.infra.configurations.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import br.dev.diisk.application.dtos.response.ErrorResponse;
import br.dev.diisk.application.interfaces.IResponseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final IResponseService responseService;

    public CustomAccessDeniedHandler(IResponseService responseService) {
        this.responseService = responseService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Integer statusCode = HttpServletResponse.SC_FORBIDDEN;
        ErrorResponse responseObject = ErrorResponse.getErrorInstance(statusCode, "You have no rights here.");
        responseService.writeResponseObject(response, statusCode, responseObject);
    }

}
