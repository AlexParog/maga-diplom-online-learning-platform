package ru.parog.magatestservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class TestConflictException extends BaseException {

    public TestConflictException(String message, Object... args) {
        super(message, args);
    }

    public TestConflictException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

}
