package ru.parog.magatestservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TestGlobalApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> unknownException(Exception unknownException) {
        log.error(unknownException.getMessage(), unknownException);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(unknownException.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(ResourceNotFoundException resourceNotFoundException) {
        log.error(resourceNotFoundException.getMessage(), resourceNotFoundException);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(resourceNotFoundException.getMessage()));
    }

    @ExceptionHandler(TestConflictException.class)
    public ResponseEntity<ErrorMessage> testConflictException(TestConflictException testConflictException) {
        log.error(testConflictException.getMessage(), testConflictException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(testConflictException.getMessage()));
    }
}
