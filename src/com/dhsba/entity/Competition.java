package com.dhsba.entity;

import com.dhsba.common.AthleteCategory;
import com.dhsba.service.CompetitionService;

import java.util.ArrayList;
import java.util.Date;

/**
 * 比赛类
 * 表示一场比赛的各种信息，这些信息在一场比赛从创建开始到完全结束这一段时间都会发生变化
 */
public class Competition implements CompetitionService {
    AthleteCategory type;
    Date startTime;
    boolean isEnd;
    int registerCount;
    int registerMax;
    ArrayList<Game> games;
    ArrayList<Athlete> competitors;

    public Competition(AthleteCategory type, Date startTime, int registerMax) {
        this.type = type;
        this.startTime = startTime;
        this.registerMax = registerMax;
    }

    @Override
    public void showInfo() {

    }

    @Override
    public void arrangeGames() {

    }

    @Override
    public void modifyCompetitorNumber(int number) {

    }

    @Override
    public void endCompetition() {

    }
}
