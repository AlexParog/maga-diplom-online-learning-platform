package ru.parog.magauserservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserGlobalApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> unknownException(Exception unknownException) {
        log.error(unknownException.getMessage(), unknownException);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(unknownException.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(UserNotFoundException userNotFoundException) {
        log.error(userNotFoundException.getMessage(), userNotFoundException);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(userNotFoundException.getMessage()));
    }

    @ExceptionHandler(UserStatisticsNotFoundException.class)
    public ResponseEntity<ErrorMessage> userStatisticsNotFoundException(UserStatisticsNotFoundException userStatisticsNotFoundException) {
        log.error(userStatisticsNotFoundException.getMessage(), userStatisticsNotFoundException);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(userStatisticsNotFoundException.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        log.error(userAlreadyExistsException.getMessage(), userAlreadyExistsException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(userAlreadyExistsException.getMessage()));
    }
}
