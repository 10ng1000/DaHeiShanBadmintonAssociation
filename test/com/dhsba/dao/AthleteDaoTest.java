package com.dhsba.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AthleteDaoTest {
    AthleteDao athleteDao = new AthleteDao();

    @Test
    void createAthlete() {
        //assertEquals(1,athleteDao.createAthlete("12345","小红","男"));
    }

    @Test
    void getInformation() {
        assertEquals("男",athleteDao.getInformation("12345").get(1));
    }

    @Test
    void updateCategory() {
        assertEquals(1,athleteDao.updateLevel("12345",1));
    }

    @Test
    void createCompetitionRecord() {
        // todo assertEquals(1,athleteDao.createCompetitionRecord("123"));
    }
}