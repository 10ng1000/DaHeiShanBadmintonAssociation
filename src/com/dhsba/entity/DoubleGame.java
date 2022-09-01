package com.dhsba.entity;

import com.dhsba.common.Round;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class DoubleGame extends Game {
    Pair<String, String> athleteA; //组合A
    Pair<String, String> athleteB; //组合B
    Pair<String, String> winner;

    public DoubleGame(int competitionId, Round round, Pair<String, String> athleteA, Pair<String, String> athleteB,
                      Pair<String, String> aNumber, Pair<String, String> bNumber) {
        super(round, athleteA, athleteB);
        gameDao.createDoubleGame(competitionId, gameId, aNumber, bNumber, round.toString());
    }

    public DoubleGame(int gameId, Round round, Pair<String, String> athleteA, Pair<String, String> athleteB,
                      ArrayList<Pair<Integer, Integer>> points) {
        super(gameId, round, athleteA, athleteB, points);
    }

    @Override
    public boolean saveGamePoint(int competitionId) {
        return gameDao.createDoubleGamePoint(competitionId, gameId,
                (Pair<String, String>) aNumber,
                (Pair<String, String>) bNumber,
                points) >= 1;
    }
}
