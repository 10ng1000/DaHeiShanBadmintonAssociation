package com.dhsba.entity;

import com.dhsba.common.Round;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class SingleGame extends Game {
    String athleteA;
    String athleteB;
    String winner;

    public SingleGame(int competitionId, Round round, String athleteA, String athleteB, String aNumber, String bNumber) {
        super(round, athleteA, athleteB);
        gameDao.createSingleGame(competitionId, gameId, aNumber, bNumber, round.toString());
    }

    public SingleGame(int gameId, Round round, String athleteA, String athleteB,
                      ArrayList<Pair<Integer, Integer>> points) {
        super(gameId, round, athleteA, athleteB, points);
    }

    @Override
    public boolean saveGamePoint(int competitionId) {
        return gameDao.createSingleGamePoint(competitionId, gameId,
                (String) aNumber, (String) bNumber
                , points) >= 1;
    }
}
