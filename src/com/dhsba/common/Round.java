package com.dhsba.common;

/**
 * 对局的轮数（半决赛等）
 */
public enum Round implements Comparable<Round> {
    elimination, quarterFinal, semiFinal, Final;

    public Round next() {
        if (this == elimination) return quarterFinal;
        if (this == quarterFinal) return semiFinal;
        if (this == semiFinal) return Final;
        return null;
    }
}
