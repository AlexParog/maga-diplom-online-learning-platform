package ru.parog.magacourseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnrollmentConflictException extends BaseException {

    public EnrollmentConflictException(String message) {
        super(message);
    }

    public EnrollmentConflictException(String message, Object... args) {
        super(message, args);
    }

}
