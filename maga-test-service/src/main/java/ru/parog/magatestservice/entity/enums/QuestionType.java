package ru.parog.magatestservice.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum QuestionType {
    SINGLE_CHOICE("SINGLE_CHOICE"), // один вариант ответа
    MULTIPLE_CHOICE("MULTIPLE_CHOICE"), // несколько вариантов ответа
    TEXT("TEXT"); // текст

    @JsonValue
    private final String value;

    QuestionType(String value) {
        this.value = value;
    }

    @JsonCreator
    public QuestionType fromString(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return value;
    }
}
