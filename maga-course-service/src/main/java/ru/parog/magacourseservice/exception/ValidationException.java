package ru.parog.magacourseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Object... args) {
        super(message, args);
    }
}
