package de.thu.thutorium;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for managing various exceptions in the application.
 * This class is annotated with {@link ControllerAdvice} to indicate that it provides global exception handling.
 * It provides a centralized way to manage and respond to errors with appropriate HTTP status codes and messages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles {@link MethodArgumentNotValidException} exceptions thrown when validation on an argument annotated with {@link jakarta.validation.Valid} fails.
     * This method captures the validation errors and returns a {@link ResponseEntity} with a {@link HttpStatus#BAD_REQUEST} status.
     *
     * @param ex the {@code MethodArgumentNotValidException} exception
     * @return a {@code ResponseEntity} containing a map of field errors and their corresponding messages
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
