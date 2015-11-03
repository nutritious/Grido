package com.codepath.grido.models;

public enum ImageColorParameter {
    ImageColorAny(0),
    ImageColorBlack(1),
    ImageColorBlue(2),
    ImageColorBrown(3),
    ImageColorGray(4),
    ImageColorGreen(5),
    ImageColorOrange(6),
    ImageColorPink(7),
    ImageColorPurple(8),
    ImageColorRed(9),
    ImageColorTeal(10),
    ImageColorWhite(11),
    ImageColorYellow(12);

    private final int value;

    private ImageColorParameter(int value) {
        this.value = value;
    }
}
