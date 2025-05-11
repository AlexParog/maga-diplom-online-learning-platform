package ru.parog.magacourseservice.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ContentItemType {
    // пока предполагается, что будет использоваться только Lesson и Test

    LESSON("LESSON"),
    TEST("TEST"),
    EXERCISE("EXERCISE"),
    IMAGE("IMAGE"),
    LINK("LINK"),
    FILE("FILE");

    @JsonValue
    private final String value;

    ContentItemType(String value) {
        this.value = value;
    }

    @JsonCreator
    public ContentItemType fromString(String value) {
        return Arrays.stream(values())
                .filter(content -> content.value.toLowerCase().equals(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return value;
    }
}
