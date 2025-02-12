package org.springBootAngular.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springBootAngular.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// this is for error 401 access deny
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
            Response errorRespond = Response.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(authException.getMessage())
                    .build();
            //response body be a in json format
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            //objectMapper.writeValueAsString(errorRespond): Converts the errorRespond object
        // into a JSON string using Jackson's ObjectMapper. This will serialize the Response
        // object into a JSON format,
            response.getWriter().write(objectMapper.writeValueAsString(errorRespond));

    }
}
