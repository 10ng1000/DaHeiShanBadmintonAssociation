package com.dhsba.common;

/**
 * 选手擅长的类别，如混双等
 */
public enum AthleteCategory {
    manSingle, womanSingle, menDouble, womenDouble, mixedDouble;

    public boolean isSingle() {
        return this == manSingle || this == womanSingle;
    }
}
