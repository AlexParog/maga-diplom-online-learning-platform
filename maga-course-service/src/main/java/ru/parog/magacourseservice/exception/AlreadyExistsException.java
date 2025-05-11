package ru.parog.magacourseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Object... args) {
        super(message, args);
    }
}
