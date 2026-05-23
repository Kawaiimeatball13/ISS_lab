package ro.iss.agorainretea.contoller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.iss.agorainretea.exceptions.ServiceException;
import ro.iss.agorainretea.domain.requests.ErrorResponse;
import ro.iss.agorainretea.exceptions.ValidationException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceExceptions(ServiceException serviceException) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), serviceException.getMessage(), "ServiceException");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException validationException) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), validationException.getMessage(), "ValidationException");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
}
