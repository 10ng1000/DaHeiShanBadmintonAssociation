package com.dhsba.dao;

import com.dhsba.entity.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GameDaoTest {
    GameDao gameDao = new GameDao();

    @Test
    void createSingleGameRecord() {
        /**assertEquals(3, gameDao.createDoubleGameRecord(
         1,Pair.of("789","678"),Pair.of("7890","6789"),
         new ArrayList<>(List.of(Pair.of(14,21),Pair.of(18,21))), "elimination"
         ));*/
    }

    @Test
    void getGames() {
        ArrayList<Game> doubleGames = gameDao.getDoubleGames(2);
        ArrayList<Game> singleGames = gameDao.getSingleGames(1);
        for (Game game : doubleGames) {
            System.out.println(game);
        }
        for (Game game : singleGames) {
            System.out.println(game);
        }
    }
}