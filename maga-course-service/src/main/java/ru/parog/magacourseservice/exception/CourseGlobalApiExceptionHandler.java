package ru.parog.magacourseservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CourseGlobalApiExceptionHandler {

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

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> alreadyExistsException(AlreadyExistsException alreadyExistsException) {
        log.error(alreadyExistsException.getMessage(), alreadyExistsException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(alreadyExistsException.getMessage()));
    }

    @ExceptionHandler(EnrollmentConflictException.class)
    public ResponseEntity<ErrorMessage> enrollmentConflictException(EnrollmentConflictException enrollmentConflictException) {
        log.error(enrollmentConflictException.getMessage(), enrollmentConflictException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(enrollmentConflictException.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessage> lessonValidationException(ValidationException lessonValidationException) {
        log.error(lessonValidationException.getMessage(), lessonValidationException);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage(lessonValidationException.getMessage()));
    }

    @ExceptionHandler(CourseNotAvailableException.class)
    public ResponseEntity<ErrorMessage> courseNotAvailableException(CourseNotAvailableException courseNotAvailableException) {
        log.error(courseNotAvailableException.getMessage(), courseNotAvailableException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(courseNotAvailableException.getMessage()));
    }

}
