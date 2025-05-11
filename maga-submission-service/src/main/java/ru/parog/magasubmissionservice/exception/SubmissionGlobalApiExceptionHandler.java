package ru.parog.magasubmissionservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SubmissionGlobalApiExceptionHandler {

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

    @ExceptionHandler(SubmissionConflictException.class)
    public ResponseEntity<ErrorMessage> enrollmentConflictException(SubmissionConflictException enrollmentConflictException) {
        log.error(enrollmentConflictException.getMessage(), enrollmentConflictException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(enrollmentConflictException.getMessage()));
    }
}
