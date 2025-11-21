package com.example.asset_qrgenerator_service.exception;

import com.example.asset_qrgenerator_service.util.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleNotFound(ResourceNotFoundException ex) {

        ApiErrorResponse<String> response = new ApiErrorResponse<>();
        response.setData(null);
        response.setMessage(ex.getMessage());
        response.setStatuscode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleBadRequest(BadRequestException ex) {

        ApiErrorResponse<String> response = new ApiErrorResponse<>();
        response.setData(null);
        response.setMessage(ex.getMessage());
        response.setStatuscode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });

        ApiErrorResponse<Object> response = new ApiErrorResponse<>();
        response.setStatuscode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(errors.toString());
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateSerialNumException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleDuplicateSerial(DuplicateSerialNumException ex) {

        ApiErrorResponse<String> response = new ApiErrorResponse<>();
        response.setData(null);
        response.setMessage(ex.getMessage());
        response.setStatuscode(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse<String>> handleGeneral(Exception ex) {

        ApiErrorResponse<String> response = new ApiErrorResponse<>();
        response.setData(null);
        response.setMessage("Internal Server Error: " + ex.getMessage());
        response.setStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
