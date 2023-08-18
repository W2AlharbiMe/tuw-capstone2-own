package com.example.capstone2.Advice;

import com.example.capstone2.Api.Exception.ResourceNotFoundException;
import com.example.capstone2.Api.Exception.SimpleException;
import com.example.capstone2.Api.Response.ErrorResponse;
import com.example.capstone2.Api.Response.SimpleApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    // START CUSTOM EXCEPTIONS
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<SimpleApiResponse> ApiException(ResourceNotFoundException e){
        String message = e.getMessage();
        return ResponseEntity.status(404).body(new SimpleApiResponse(message));
    }

    @ExceptionHandler(value = SimpleException.class)
    public ResponseEntity<SimpleApiResponse> ApiException(SimpleException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(message));
    }

    // END CUSTOM EXCEPTIONS

    // Server Validation Exception
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ArrayList<ErrorResponse> errorResponses = new ArrayList<>();

        for(FieldError fieldError: e.getFieldErrors()) {
            ErrorResponse errorResponse = new ErrorResponse(fieldError.getObjectName(), fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getCode());
            errorResponses.add(errorResponse);
        }

        return ResponseEntity.status(400).body(errorResponses);
    }

    // Server Validation Exception
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<SimpleApiResponse> ConstraintViolationException(ConstraintViolationException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }


    // SQL Constraint Ex:(Duplicate) Exception
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<SimpleApiResponse> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }

    // wrong write SQL in @column Exception
    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class )
    public ResponseEntity<SimpleApiResponse> InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }

    // Database Constraint Exception
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<SimpleApiResponse> DataIntegrityViolationException(DataIntegrityViolationException e){
        String msg = e.getMessage();
        System.out.println(e.getCause().toString());

        if(e.getMessage().toLowerCase().contains("duplicate entry")) {
            return ResponseEntity.status(400).body(new SimpleApiResponse("duplicated entry."));
        }

        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }

    // Method not allowed Exception
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<SimpleApiResponse> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }

    // Json parse Exception
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<SimpleApiResponse> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }

    // TypesMisMatch Exception
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<SimpleApiResponse> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new SimpleApiResponse(msg));
    }
}
