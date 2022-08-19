package com.dhsba.entity;

import com.dhsba.common.Round;
import com.dhsba.service.GameService;

import java.util.ArrayList;

/**
 * 表示三局两胜中的“局”
 * 显示大比分和小比分
 */
public class Game implements GameService {
    Round round;
    Athlete a;
    Athlete b;
    ArrayList<Integer> aPoints;
    ArrayList<Integer> bPoints;
    int aWins;
    int bWins;
    boolean isEnd;

    public Game(Round round, Athlete a, Athlete b) {
        this.round = round;
        this.a = a;
        this.b = b;
    }
}
