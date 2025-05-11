package ru.parog.magasubmissionservice.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SubmissionStatus {
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    @JsonValue
    private final String value;

    SubmissionStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public SubmissionStatus fromString(String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return value;
    }
}
