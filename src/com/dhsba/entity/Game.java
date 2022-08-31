package com.dhsba.entity;

import com.dhsba.common.AthleteCategory;
import com.dhsba.common.Round;
import com.dhsba.dao.GameDao;
import com.dhsba.service.GameService;
import com.dhsba.service.ShowAble;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

/**
 * 表示两个成员在该轮的对局，默认为三局两胜
 * 显示大比分和小比分
 * 子类实现单双打
 */
public abstract class Game implements GameService, Comparable<Game>, ShowAble {
    public static final int WIN_POINT = 21;
    GameDao gameDao = new GameDao();
    Round round;
    Object athleteA;
    Object athleteB;
    ArrayList<Pair<Integer, Integer>> points = null;
    int aWins;
    int bWins;
    Object winner;

    /**
     * 创建新的对局
     *
     * @param round
     * @param athleteA
     * @param athleteB
     */
    public Game(Round round, Object athleteA, Object athleteB) {
        this.round = round;
        this.athleteA = athleteA;
        this.athleteB = athleteB;
    }

    /**
     * 创建已经完成的对局
     * @param round
     * @param athleteA
     * @param athleteB
     * @param points
     */
    public Game(Round round, Object athleteA, Object athleteB,
                ArrayList<Pair<Integer, Integer>> points) {
        this.round = round;
        this.athleteA = athleteA;
        this.athleteB = athleteB;
        this.points = points;
        for (Pair<Integer, Integer> point : this.points) {
            if (point.getLeft() == WIN_POINT) aWins++;
            if (point.getRight() == WIN_POINT) bWins++;
            if (aWins == 2) winner = athleteA;
            if (bWins == 2) winner = athleteB;
        }
    }

    /**
     * 创建没有进行的对局
     * @param type     比赛类型：如单打或是双打
     * @param round    轮数
     * @param athleteA 如果是单打，则为字符串类型；如果是双打，则为Pair<String, String>类型
     * @param athleteB 同A
     * @return
     */
    public static Game createGame(String type, Round round, Object athleteA, Object athleteB) {
        if (AthleteCategory.valueOf("type") == AthleteCategory.manSingle
                || AthleteCategory.valueOf("type") == AthleteCategory.womanSingle) {
            return new SingleGame(round, (String) athleteA, (String) athleteB);
        } else {
            return new DoubleGame(round, (Pair<String, String>) athleteA, (Pair<String, String>) athleteB);
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "round=" + round +
                ", aName='" + athleteA + '\'' +
                ", bName='" + athleteB + '\'' +
                ", points=" + points +
                ", aWins=" + aWins +
                ", bWins=" + bWins +
                ", winner='" + winner + '\'' +
                '}';
    }

    @Override
    public boolean changePoint(Pair<Integer, Integer> newPoint) {
        points.add(newPoint);
        if (newPoint.getLeft() == WIN_POINT) aWins++;
        else if (newPoint.getRight() == WIN_POINT) bWins++;
        if (aWins == 2) {
            winner = athleteA;
            return true;
        } else if (bWins == 2) {
            winner = athleteB;
            return true;
        }
        return false;
    }

    /**
     * 显示选手比分
     */
    @Override
    public void showInfo() {
        System.out.println("运动员：" + athleteA + '，' + athleteB);
        for (Pair<Integer, Integer> point : points) {
            System.out.println(point.getLeft().toString() + '：' + point.getRight().toString());
        }
        if (winner != null) {
            System.out.println("获胜者：" + winner);
        }
        System.out.println();
    }

    @Override
    public int compareTo(Game game) {
        return this.round.compareTo(game.round);
    }

    public Round getRound() {
        return round;
    }

    @Override
    public abstract boolean saveGame(int competitionId, Participant athleteA, Participant athleteB);
}
