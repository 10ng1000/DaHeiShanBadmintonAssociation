package com.dhsba.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AthleteDaoTest {
    AthleteDao athleteDao = new AthleteDao();

    @Test
    void createAthlete() {
        /**
         assertEquals(1,athleteDao.createAthlete("789","小红","女"));
         assertEquals(1,athleteDao.createAthlete("678","小蓝","男"));
         assertEquals(1,athleteDao.createAthlete("7890","小青","女"));
         assertEquals(1,athleteDao.createAthlete("6789","小黄","男"));
         */
    }

    @Test
    void getInformation() {
        assertEquals("男",athleteDao.getInformation("12345").get(1));
    }

    @Test
    void updateCategory() {
        //assertEquals(1,athleteDao.updateLevel("12345",1));
    }

    @Test
    void createCompetitionRecord() {
        //assertEquals(1,athleteDao.createCompetitionRecord("123", 1));
    }
}