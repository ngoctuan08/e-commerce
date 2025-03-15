package com.ngoctuan.ecommerce.exception.handler;

import com.ngoctuan.ecommerce.exception.BadRequestException;
import com.ngoctuan.ecommerce.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetails> handleDataViolationException(Exception ex,
                                                                           WebRequest request) {

        return getErrorDetailsResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleNotFoundException(Exception ex,
                                                                      WebRequest request) {

        return getErrorDetailsResponseEntity(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(BadRequestException ex, WebRequest request) {

        return getErrorDetailsResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDetails> getErrorDetailsResponseEntity(Exception ex,
                                                                       WebRequest request, HttpStatus httpStatus) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(),
                request.getDescription(false));
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorDetails, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {

        return getErrorDetailsResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
