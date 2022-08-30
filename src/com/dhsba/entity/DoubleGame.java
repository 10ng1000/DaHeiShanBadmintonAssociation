package com.dhsba.entity;

import com.dhsba.common.Round;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class DoubleGame extends Game {
    Pair<String, String> athleteA; //组合A
    Pair<String, String> athleteB; //组合B
    Pair<String, String> winner;

    public DoubleGame(Round round, Pair<String, String> athleteA, Pair<String, String> athleteB) {
        super(round, athleteA, athleteB);
    }

    public DoubleGame(Round round, Pair<String, String> athleteA, Pair<String, String> athleteB,
                      ArrayList<Pair<Integer, Integer>> points) {
        super(round, athleteA, athleteB, points);
    }

    @Override
    public boolean saveGame(int competitionId, Participant athleteA, Participant athleteB) {
        return gameDao.createDoubleGameRecord(competitionId,
                (Pair<String, String>) athleteA.getAccountNumber(),
                (Pair<String, String>) athleteB.getAccountNumber(),
                points, round.toString()) >= 1;
    }
}
