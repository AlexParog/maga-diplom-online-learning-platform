package ru.parog.magauserservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Object... args) {
        super(message, args);
    }

}
