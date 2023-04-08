package com.kb.learn.exception;

import com.kb.learn.api.module.ApiResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private static final String ERROR_MESSAGE = "Unable to process the request due to an unexpected error occurred.";

    private static String getRequestedUri(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private static void addTimestamp(final ProblemDetail problemDetail) {
        problemDetail.setProperty("timestamp", Instant.now());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ERROR_MESSAGE, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create(getRequestedUri(request)));
        addTimestamp(problemDetail);
        return ResponseEntity.ok(ApiResponseEntity.builder().body(problemDetail).build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ERROR_MESSAGE, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
        problemDetail.setType(URI.create(getRequestedUri(request)));
        addTimestamp(problemDetail);
        return ResponseEntity.ok(ApiResponseEntity.builder().body(problemDetail).build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ERROR_MESSAGE, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
        problemDetail.setType(URI.create(getRequestedUri(request)));
        addTimestamp(problemDetail);
        return ResponseEntity.ok(ApiResponseEntity.builder().body(problemDetail).build());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, ex.getLocalizedMessage());
        problemDetail.setType(URI.create(getRequestedUri(request)));
        addTimestamp(problemDetail);
        return ResponseEntity.ok(ApiResponseEntity.builder().body(problemDetail).build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, ex.getLocalizedMessage());
        problemDetail.setType(URI.create(getRequestedUri(request)));
        addTimestamp(problemDetail);
        return ResponseEntity.ok(ApiResponseEntity.builder().body(problemDetail).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, ex.getLocalizedMessage());
        problemDetail.setType(URI.create(getRequestedUri(request)));
        addTimestamp(problemDetail);
        return ResponseEntity.ok(ApiResponseEntity.builder().body(problemDetail).build());
    }


}
