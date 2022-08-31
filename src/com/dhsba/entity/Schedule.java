package com.dhsba.entity;

import com.dhsba.dao.CompetitionDao;

import java.util.ArrayList;

/**
 * 程序启动时，加载数据库中所有的比赛
 * 程序运行时可以调用方法加入比赛到日程
 * 比赛排列好序
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
        competitions.sort(Competition::compareTo);
    }

    public static Schedule getInstance() {
        return schedule;
    }

    public void showSchedule() {
        System.out.println("比赛日程：");
        for (Competition competition : schedule.competitions) {
            System.out.println(competition);
        }
    }

    /**
     * 把比赛加入到日程，不记入数据库
     *
     * @param competition
     */
    public void addCompetition(Competition competition) {
        competitions.add(competition);
        competitions.sort(Competition::compareTo);
    }

    /**
     * @param id Competition_id
     * @return 不存在则返回null
     */
    public Competition getCompetition(int id) {
        for (Competition competition : competitions) {
            if (competition.getCompetitionId() == id) return competition;
        }
        return null;
    }
}
