package ru.guhar4k.catfeeder.model.exception;

import lombok.Getter;

@Getter
public class MotorStuckException extends RuntimeException {
    private final int successEdgesCount;

    public MotorStuckException(String message, int successEdgesCount) {
        super(message);
        this.successEdgesCount = successEdgesCount;
    }
}
