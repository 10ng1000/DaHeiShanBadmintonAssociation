package com.dhsba.entity;

import com.dhsba.common.Round;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class SingleGame extends Game {
    String athleteA;
    String athleteB;
    String winner;

    public SingleGame(Round round, String athleteA, String athleteB) {
        super(round, athleteA, athleteB);
    }

    public SingleGame(Round round, String athleteA, String athleteB,
                      ArrayList<Pair<Integer, Integer>> points) {
        super(round, athleteA, athleteB, points);
    }

    @Override
    public boolean saveGame(int competitionId, Participant athleteA, Participant athleteB) {
        return gameDao.createSingleGameRecord(competitionId,
                (String) athleteA.getAccountNumber(), (String) athleteB.getAccountNumber()
                , points, round.toString()) >= 1;
    }
}
