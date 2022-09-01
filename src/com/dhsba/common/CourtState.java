package com.dhsba.common;

/**
 * 场地的四个状态
 */
public enum CourtState {
    available, commonReserve, training, inCompetition;

    public static String allReserveToString() {
        return commonReserve + "," + training + "," + inCompetition;
    }
}
