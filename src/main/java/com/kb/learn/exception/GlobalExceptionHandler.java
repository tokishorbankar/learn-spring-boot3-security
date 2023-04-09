package com.kb.learn.exception;

import com.kb.learn.module.ApiResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_MESSAGE = "Unable to process the request due to an unexpected error occurred. Message:: {}, Error:: {}";

    public GlobalExceptionHandler() {
        super();
    }

    private static String getUri(final WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private static ApiResponse<ProblemDetail> getErrorResponse(final ProblemDetail body, final WebRequest request) {
        body.setType(URI.create(getUri(request)));
        body.setProperty("timestamp", Instant.now());
        return new ApiResponse<>(body);
    }

    private static <T> List<String> apiValidationErrorMapping(T ex) {
        return ((BindException) ex).getBindingResult().getFieldErrors().stream()
                .map(GlobalExceptionHandler :: apiValidationErrorMapping).collect(Collectors.toList());
    }

    private static String apiValidationErrorMapping(FieldError fieldError) {
        return new ApiValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()).toString();
    }

    // API

    // 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        final BindingResult result = ex.getBindingResult();

        List<String> message = apiValidationErrorMapping(ex);
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        body.setDetail("Invalid argument exception");
        body.setProperty("cause", message);

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        Object[] args = {ex.getPropertyName(), ex.getValue()};
        String defaultDetail = "Failed to convert '" + args[0] + "' with value: '" + args[1] + "'";
        String messageCode = ErrorResponse.getDefaultDetailMessageCode(TypeMismatchException.class, null);
        ProblemDetail body = createProblemDetail(ex, status, defaultDetail, messageCode, args, request);

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        final String error = ex.getParameterName() + " part is missing";
        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        body.setDetail(error);

        return ResponseEntity.ok(getErrorResponse(body, request));
    }


    @ExceptionHandler({UnauthorizedUserException.class})
    public ResponseEntity<Object> handleUnauthorizedUserException(
            RuntimeException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(
            RuntimeException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            RuntimeException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            RuntimeException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtException(
            JwtException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternal(
            RuntimeException ex, WebRequest request) {
        log.error(ERROR_MESSAGE, ex.getMessage(), ex);

        ProblemDetail body = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return ResponseEntity.ok(getErrorResponse(body, request));
    }

}
