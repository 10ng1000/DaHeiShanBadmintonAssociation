package com.dhsba.entity;

import com.dhsba.dao.CompetitionDao;

import java.util.ArrayList;

/**
 * 在创建对象时，加载数据库中所有的比赛
 * 程序运行时可以调用方法加入比赛到日程
 * 单例模式
 */
public class Schedule {
    private static final Schedule schedule = new Schedule();
    ArrayList<Competition> competitions;

    /**
     * 在创建对象时，加载数据库中所有的比赛
     */
    private Schedule() {
        CompetitionDao competitionDao = new CompetitionDao();
        ArrayList<Competition> gotCompetition = competitionDao.getCompetitions();
        competitions.addAll(gotCompetition);
    }

    public static Schedule getInstance() {
        return schedule;
    }

    public static void showSchedule() {
        System.out.println("比赛日程：");
        for (Competition competition : schedule.competitions) {
            System.out.println(competition.getStartTime());
        }
    }

    public void addCompetition(Competition competition) {
        competitions.add(competition);
        competitions.sort(Competition::compareTo);
    }
}
