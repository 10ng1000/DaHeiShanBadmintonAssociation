package com.dhsba.common;

import java.util.Arrays;
import java.util.List;

/**
 * 选手擅长的类别，如混双等
 */
public enum AthleteCategory {
    manSingle, womanSingle, menDouble, womenDouble, mixedDouble;

    public boolean isSingle() {
        return this == manSingle || this == womanSingle;
    }

    public static String allToString() {
        return manSingle + "," +
                womanSingle + "," +
                menDouble + "," +
                womenDouble + ","
                + mixedDouble;
    }

    public static List<AthleteCategory> getManType() {
        return Arrays.asList(manSingle, menDouble, mixedDouble);
    }

    public static List<AthleteCategory> getWomanType() {
        return Arrays.asList(womanSingle, womenDouble, mixedDouble);
    }
}
