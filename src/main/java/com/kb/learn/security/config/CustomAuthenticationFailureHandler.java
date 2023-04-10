package com.kb.learn.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String ERROR_MESSAGE = "Unable to process the request due to an unexpected error occurred. Message:: {}, Error:: {}";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException ex) throws IOException, ServletException {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", LocalDate.now());
        data.put("exception", ex.getMessage());

        response
                .getOutputStream()
                .println(objectMapper.writeValueAsString(data));

    }
}
