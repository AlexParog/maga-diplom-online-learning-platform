package ru.parog.onlinelearningplatformmodel.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public static Supplier<BaseException> baseException(String message, Object... args) {
        return () -> new BaseException(message, args);
    }

    public static Supplier<BaseException> baseException(String message) {
        return () -> new BaseException(message);
    }
}
