package com.school.ecommerce.controllers.advices;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import com.school.ecommerce.controllers.exceptions.AccessDeniedException;
import com.school.ecommerce.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerce.controllers.exceptions.UniqueConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        String message = ex.getMessage();

        errors.put("message", message);

        BaseResponse baseResponse = BaseResponse.builder()
                .data(errors)
                .message("Operation failed")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<BaseResponse> handleInvalidFormatException(InvalidFormatException ex) {
        Map<String, String> errors = new HashMap<>();


        errors.put("message", "Invalid format date");

        BaseResponse baseResponse = BaseResponse.builder()
                .data(errors)
                .message("Operation failed")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<BaseResponse> handleObjectNotFoundException(ObjectNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();

        String message = ex.getMessage();

        errors.put("message", message);

        BaseResponse baseResponse = BaseResponse.builder()
                .data(errors)
                .message("Operation failed")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", "Access to the requested resource is forbidden");

        BaseResponse baseResponse = BaseResponse.builder()
                .data(errors)
                .message("Operation failed")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.FORBIDDEN).build();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }
}
