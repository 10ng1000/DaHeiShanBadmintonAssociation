package com.dhsba.entity;

import com.dhsba.common.AthleteCategory;
import com.dhsba.common.Round;
import com.dhsba.dao.CompetitionDao;
import com.dhsba.service.CompetitionService;
import com.dhsba.service.ShowAble;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * 比赛类
 * 表示一场比赛的各种信息，这些信息在一场比赛从创建开始到完全结束这一段时间都会发生变化
 */
public class Competition implements CompetitionService, Comparable<Competition>, ShowAble {
    int competitionId;
    CompetitionDao competitionDao = new CompetitionDao();
    AthleteCategory type;
    Date startTime;
    boolean isEnd;
    int registerCount;
    int registerMax;
    ArrayList<Game> games;
    ArrayList<Participant> participants;

    /**
     * 管理员创建比赛
     *
     * @param type
     * @param startTime
     * @param registerMax
     */
    public Competition(AthleteCategory type, Date startTime, int registerMax) {
        this.type = type;
        this.startTime = startTime;
        this.registerMax = registerMax;
        this.competitionId = competitionDao.getNewestId();
        Timer timer = new Timer();
        CompetitionTimerTask timerTask = new CompetitionTimerTask();
        timer.schedule(timerTask, startTime);
    }

    /**
     * 从数据库里读取比赛
     *
     * @param type
     * @param startTime
     * @param registerCount
     * @param registerMax
     * @param games
     * @param competitionId
     */
    public Competition(AthleteCategory type, Date startTime, int registerCount, int registerMax,
                       ArrayList<Game> games, int competitionId) {
        this.type = type;
        this.startTime = startTime;
        this.registerCount = registerCount;
        this.registerMax = registerMax;
        this.games = games;
        this.competitionId = competitionId;
    }

    /**
     * 显示详细信息
     */
    @Override
    public void showInfo() {
        System.out.println(this);
        showGameInfo();
    }

    /**
     * 显示简略信息
     *
     * @return
     */
    @Override
    public String toString() {
        return "Id =" + competitionId +
                ", 类型=" + type +
                ", 开始时间=" + startTime +
                ", 报名人数=" + registerCount +
                ", 人数上限=" + registerMax;
    }

    @Override
    public void showGameInfo() {
        System.out.println("赛程信息");
        System.out.println("---16强---");
        for (Game game : games) {
            if (game.getRound() == Round.elimination) game.showInfo();
        }
        System.out.println("---8强---");
        for (Game game : games) {
            if (game.getRound() == Round.quarterFinal) game.showInfo();
        }
        System.out.println("---半决赛---");
        for (Game game : games) {
            if (game.getRound() == Round.semiFinal) game.showInfo();
        }
        System.out.println("---决赛---");
        for (Game game : games) {
            if (game.getRound() == Round.Final) game.showInfo();
        }
    }

    @Override
    public void addParticipant(Participant participant) {
        this.participants.add(participant);
        if (!type.isSingle()) {
            Pair<String, String> pair = (Pair<String, String>) participant.getAccountNumber();
            competitionDao.createParticipantRecord(pair.getLeft(), competitionId);
            competitionDao.createParticipantRecord(pair.getRight(), competitionId);
        } else {
            competitionDao.createParticipantRecord((String) participant.getAccountNumber(), competitionId);
        }
        registerCount++;
    }

    @Override
    public void arrangeGames() {
        Collections.shuffle(participants);
        Round round = Round.elimination;
        if (registerCount > 8 && registerCount <= 16) round = Round.elimination;
        else if (registerCount > 4 && registerCount <= 8) round = Round.quarterFinal;
        else if (registerCount > 2 && registerCount <= 4) round = Round.semiFinal;
        else if (registerCount == 2) round = Round.Final;
        for (int i = 0; i < registerCount; i += 2)
            Game.createGame(
                    type.toString(), round, participants.get(i), participants.get(i + 1));
    }

    @Override
    public void deleteParticipant(Object number) {
        for (Participant participant : participants) {
            if (participant.getAccountNumber() == number) {
                participants.remove(participant);
                if (!type.isSingle()) {
                    Pair<String, String> pair = (Pair<String, String>) participant.getAccountNumber();
                    competitionDao.deleteParticipantRecord(pair.getLeft(), competitionId);
                    competitionDao.deleteParticipantRecord(pair.getRight(), competitionId);
                } else {
                    competitionDao.deleteParticipantRecord((String) participant.getAccountNumber(), competitionId);
                }
                registerCount--;
            }
        }
        registerCount--;
    }

    /**
     * 将比赛以及对局记录写入数据库
     */
    @Override
    public void endCompetition() {
        competitionDao.createCompetitionRecord(type.toString(), registerCount, registerMax, startTime, competitionId);
        for (Participant participant : participants) {
            if (type.isSingle()) ((Athlete) participant.getAthlete()).setInCompetition(false);
            else {
                ((Pair<Athlete, Athlete>) participant.getAthlete()).getLeft().setInCompetition(false);
                ((Pair<Athlete, Athlete>) participant.getAthlete()).getRight().setInCompetition(false);
            }
        }
        isEnd = true;
    }

    @Override
    public boolean canParticipate() {
        return registerCount < registerMax && startTime.after(new Date(System.currentTimeMillis()));
    }

    public int getCompetitionId() {
        return competitionId;
    }

    /**
     * 日期较晚的排于较前
     *
     * @param competition
     * @return
     */
    @Override
    public int compareTo(Competition competition) {
        return -this.startTime.compareTo(competition.startTime);
    }

    public Date getStartTime() {
        return startTime;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public AthleteCategory getType() {
        return type;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    class CompetitionTimerTask extends TimerTask {
        public void run() {
            arrangeGames();
        }
    }
}
