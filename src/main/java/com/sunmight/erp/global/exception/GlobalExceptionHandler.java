package com.sunmight.erp.global.exception;

import com.sunmight.erp.global.exception.dto.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundBusinessException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundBusinessException e) {

        ErrorResponse response = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND.getStatus(),
                ErrorCode.ENTITY_NOT_FOUND.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // @Valid 에러 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse response = new ErrorResponse(
                ErrorCode.VALIDATION_ERROR.getStatus(),
                ErrorCode.VALIDATION_ERROR.getCode(),
                errors.toString()
        );

        return ResponseEntity.badRequest().body(response);
    }
}