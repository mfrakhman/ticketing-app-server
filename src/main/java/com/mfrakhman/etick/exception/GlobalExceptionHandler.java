package com.mfrakhman.etick.exception;

import com.mfrakhman.etick.dto.FieldError;
import com.mfrakhman.etick.response.Response;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 🟩 Handle @Valid DTO validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<List<FieldError>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> FieldError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .code(fieldError.getCode())
                        .build())
                .toList();

        return Response.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
    }

    // 🟨 Handle validation errors on query params or path vars
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<List<FieldError>>> handleConstraintViolation(ConstraintViolationException ex) {
        List<FieldError> errors = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String fieldName = violation.getPropertyPath().toString();
                    int lastDotIndex = fieldName.lastIndexOf('.');
                    if (lastDotIndex != -1) {
                        fieldName = fieldName.substring(lastDotIndex + 1);
                    }

                    return FieldError.builder()
                            .field(fieldName)
                            .message(violation.getMessage())
                            .build();
                }).toList();

        return Response.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
    }

//    @ExceptionHandler({EntityNotFoundException.class, UserNotFoundException.class})
//    public ResponseEntity<Response<Object>> handleNotFound(RuntimeException ex) {
//        return Response.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<Object>> handleBadCredentials(BadCredentialsException ex) {
        return Response.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response<Object>> handleBadRequest(BadRequestException ex) {
        return Response.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
        return Response.error(HttpStatus.BAD_REQUEST.value(), "Invalid JSON format");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response<Object>> handleMissingParameter(MissingServletRequestParameterException ex) {
        return Response.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

//    @ExceptionHandler(MessagingException.class)
//    public ResponseEntity<Response<Object>> handleMessagingException(MessagingException ex) {
//        log.error("Email sending failed: {}", ex.getMessage(), ex);
//        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Email sending failed");
//    }
//
//
//    @ExceptionHandler(RedisOperationException.class)
//    public ResponseEntity<Response<Object>> handleRedisOperationException(RedisOperationException ex) {
//        log.error("Redis operation failed: {}", ex.getMessage(), ex);
//        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
//    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<Object>> handleResponseStatusException(ResponseStatusException ex) {
        if (ex.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            log.error("Response status exception: {}", ex.getMessage(), ex);
        }
        return Response.error(ex.getStatusCode().value(), ex.getReason());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response<Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return Response.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return Response.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<Response<Object>> handlePropertyValueException(PropertyValueException ex) {
        String field = ex.getPropertyName();
        String message = String.format("Missing required field: %s", field);
        return Response.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Data integrity violation";

        if (ex.getRootCause() != null && ex.getRootCause().getMessage() != null) {
            String rootMessage = ex.getRootCause().getMessage().toLowerCase();

            if (rootMessage.contains("duplicate") || rootMessage.contains("unique")) {
                message = "Duplicate data detected. A record with this value already exists.";
            } else if (rootMessage.contains("foreign key")) {
                message = "Operation violates a foreign key constraint.";
            } else if (rootMessage.contains("not-null")) {
                message = "Missing required field. One or more fields cannot be null.";
            }
        }

        return Response.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleGeneralException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }
}
