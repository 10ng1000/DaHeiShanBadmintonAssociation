package com.dhsba.dao;

import com.dhsba.entity.Competition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CompetitionDaoTest {
    CompetitionDao competitionDao = new CompetitionDao();

    @Test
    void testCreateCompetitionRecord() {
        /**
         assertEquals(1, competitionDao.createCompetitionRecord(
         "womanSingle", 16, 16, new Date(System.currentTimeMillis())
         ));
         */
    }

    @Test
    void getCompetitions() {
        ArrayList<Competition> competitions = competitionDao.getCompetitions();
        for (Competition competition : competitions) {
            competition.showGameInfo();
        }
        System.out.println(competitionDao.getCompetitions());
    }

    @Test
    void createSignUpRecord() {
        /**
         assertEquals(1, competitionDao.createSignUpRecord(2,"678"));
         assertEquals(1, competitionDao.createSignUpRecord(2,"789"));
         assertEquals(1, competitionDao.createSignUpRecord(2,"6789"));
         assertEquals(1, competitionDao.createSignUpRecord(2,"7890"));
         */
    }

    @Test
    void getAthleteCompetitions() {
        ArrayList<Competition> competitions = competitionDao.getAthleteCompetitions("678");
        for (Competition competition : competitions) {
            competition.showGameInfo();
        }
        System.out.println(competitions);
    }
}