package ru.guhar4k.catfeeder.model.enumeration;

public enum Gain {
    GAIN_128(128),
    GAIN_64(64),
    GAIN_32(32);

    Gain(int gainValue) {
        this.gainValue = gainValue;
    }

    private final int gainValue;

    public int getValue() {
        return gainValue;
    }
}
