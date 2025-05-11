package ru.parog.magasubmissionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.parog.onlinelearningplatformmodel.exception.BaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubmissionConflictException extends BaseException {

    public SubmissionConflictException(String message) {
        super(message);
    }

    public SubmissionConflictException(String message, Object... args) {
        super(message, args);
    }

}
