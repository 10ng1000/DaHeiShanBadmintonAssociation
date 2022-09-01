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
    static GameDao gameDao = new GameDao();
    int gameId;
    Round round;
    Object athleteA;
    Object athleteB;
    Object aNumber;
    Object bNumber;
    ArrayList<Pair<Integer, Integer>> points;
    int aWins;
    int bWins;
    Object winner;
    Object winnerNumber;

    /**
     * 创建新的对局
     * @param round
     * @param athleteA
     * @param athleteB
     */
    public Game(Round round, Object athleteA, Object athleteB) {
        this.round = round;
        this.athleteA = athleteA;
        this.athleteB = athleteB;
        this.gameId = gameDao.getNewestGameId();
        this.points = new ArrayList<>();
    }

    /**
     * 创建已经完成的对局
     *
     * @param round
     * @param athleteA
     * @param athleteB
     * @param points
     */
    public Game(int gameId, Round round, Object athleteA, Object athleteB,
                ArrayList<Pair<Integer, Integer>> points) {
        this.round = round;
        this.athleteA = athleteA;
        this.athleteB = athleteB;
        this.points = points;
        this.gameId = gameId;
        for (Pair<Integer, Integer> point : this.points) {
            if (point.getLeft() == WIN_POINT) aWins++;
            if (point.getRight() == WIN_POINT) bWins++;
            if (aWins == 2) winner = athleteA;
            if (bWins == 2) winner = athleteB;
        }
    }

    /**
     * 创建没有进行的对局
     *
     * @param type     比赛类型：如单打或是双打
     * @param round    轮数
     * @param athleteA 如果是单打，则为Athlete类型；如果是双打，则为Pair<Athlete, Athlete>类型
     * @param athleteB 同A
     * @return
     */
    public static Game createGame(int competitionId, String type, Round round, Object athleteA, Object athleteB) {
        if (AthleteCategory.valueOf(type) == AthleteCategory.manSingle
                || AthleteCategory.valueOf(type) == AthleteCategory.womanSingle) {
            return new SingleGame(competitionId, round, ((Athlete) athleteA).getName(), ((Athlete) athleteB).getName(),
                    ((Athlete) athleteA).getAccountNumber(), ((Athlete) athleteB).getAccountNumber());
        } else {
            return new DoubleGame(competitionId, round,
                    Pair.of((((Pair<Athlete, Athlete>) athleteA).getLeft()).getName(),
                            (((Pair<Athlete, Athlete>) athleteA).getRight()).getName()),
                    Pair.of((((Pair<Athlete, Athlete>) athleteB).getLeft()).getName(),
                            (((Pair<Athlete, Athlete>) athleteB).getRight()).getName()),
                    Pair.of((((Pair<Athlete, Athlete>) athleteA).getLeft()).getAccountNumber(),
                            (((Pair<Athlete, Athlete>) athleteA).getRight()).getAccountNumber()),
                    Pair.of((((Pair<Athlete, Athlete>) athleteB).getLeft()).getAccountNumber(),
                            (((Pair<Athlete, Athlete>) athleteB).getRight()).getAccountNumber()));
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
    public boolean changePoint(int competitionId, Pair<Integer, Integer> newPoint) {
        points.add(newPoint);
        if (newPoint.getLeft() == WIN_POINT) aWins++;
        else if (newPoint.getRight() == WIN_POINT) bWins++;
        if (aWins == 2) {
            winner = athleteA;
            winnerNumber = aNumber;
            saveGamePoint(competitionId);
            return true;
        } else if (bWins == 2) {
            winner = athleteB;
            winnerNumber = bNumber;
            saveGamePoint(competitionId);
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
    public abstract boolean saveGamePoint(int competitionId);

    public Object getWinnerNumber() {
        return winnerNumber;
    }
}
