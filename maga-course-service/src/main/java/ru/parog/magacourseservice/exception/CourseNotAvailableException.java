package ru.parog.magacourseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class CourseNotAvailableException extends BaseException {

    public CourseNotAvailableException(String message) {
        super(message);
    }

    public CourseNotAvailableException(String message, Object... args) {
        super(message, args);
    }
}
