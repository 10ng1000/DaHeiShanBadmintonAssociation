package com.dhsba.dao;

import com.dhsba.common.AthleteCategory;
import com.dhsba.entity.Competition;
import com.dhsba.entity.Game;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * 记录比赛接口和获得数据库中的比赛的接口
 */
public class CompetitionDao extends BaseDao {

    /**
     * 初步记录比赛
     * @param category
     * @param regMax
     * @param startTime
     * @return 受影响的行数
     */
    public int createCompetitionRecord(String category, int regMax, Date startTime, int competitionId) {
        String sql = "insert into competition " +
                "(competition_id, category, reg_count, reg_max, start_time)" +
                "values (?, ?, 0, ?, ?)";
        Object[] param = {competitionId, category, regMax, startTime};
        return this.executeUpdate(sql, param);
    }

    public int updateRegisterCount(int competitionId, int count) {
        String sql = "update competition set reg_count = ? where competition_id = ?";
        Object[] param = {count, competitionId};
        return this.executeUpdate(sql, param);
    }

    /**
     * 创建比赛记录
     * @param accountNumber
     * @param competitionId
     * @return
     */
    public int createParticipantRecord(String accountNumber, int competitionId) {
        String sql = "insert into athlete_in_competition (athlete_number, competition_id) values(?,?)";
        Object[] param = {accountNumber, competitionId};
        return this.executeUpdate(sql, param);
    }

    public int deleteParticipantRecord(String accountNumber, int competitionId) {
        String sql = "delete from athlete_in_competition where athlete_number = ? and competition_id = ?";
        Object[] param = {accountNumber, competitionId};
        return this.executeUpdate(sql, param);
    }

    /**
     * @return 返回数据库中所有比赛（包括对局记录），如果没有比赛则返回空数组
     */
    public ArrayList<Competition> getCompetitions() {
        GameDao gameDao = new GameDao();
        String sql = "select competition_id, category, reg_count, reg_max, start_time from competition";
        ArrayList<Object> objects = this.executeQuery(Object.class, sql, null);
        ArrayList<Competition> competitions = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        for (int i = 0; i < objects.size(); i += 5) {
            if (AthleteCategory.valueOf((String) objects.get(i + 1)) == AthleteCategory.manSingle
                    || AthleteCategory.valueOf((String) objects.get(i + 1)) == AthleteCategory.womanSingle
            ) {
                games.addAll(gameDao.getSingleGames((int) objects.get(i)));
            } else {
                games.addAll(gameDao.getDoubleGames((int) objects.get(i)));
            }
            try {
                competitions.add(new Competition(
                        AthleteCategory.valueOf((String) objects.get(i + 1)),
                        Date.from(((LocalDateTime) objects.get(i + 4)).atZone(ZoneId.systemDefault()).toInstant()),
                        (int) objects.get(i + 2), (int) objects.get(i + 3), games, (int) objects.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            games = new ArrayList<>();
        }
        return competitions;
    }

    /**
     * @param account_number
     * @return 返回数据库中指定运动员的所有比赛（包括对局记录），如果没有比赛则返回空数组
     */
    public ArrayList<Competition> getAthleteCompetitions(String account_number) {
        GameDao gameDao = new GameDao();
        String sql = "select competition_id, category, reg_count, reg_max, start_time from competition " +
                "natural join athlete_in_competition where athlete_number = ?";
        Object[] param = {account_number};
        ArrayList<Object> objects = this.executeQuery(Object.class, sql, param);
        ArrayList<Competition> competitions = new ArrayList<>();
        ArrayList<Game> games = new ArrayList<>();
        for (int i = 0; i < objects.size(); i += 5) {
            if (AthleteCategory.valueOf((String) objects.get(i + 1)) == AthleteCategory.manSingle
                    || AthleteCategory.valueOf((String) objects.get(i + 1)) == AthleteCategory.womanSingle
            ) {
                games.addAll(gameDao.getSingleGames((int) objects.get(i)));
            } else {
                games.addAll(gameDao.getDoubleGames((int) objects.get(i)));
            }
            competitions.add(new Competition(
                    AthleteCategory.valueOf((String) objects.get(i + 1)),
                    Date.from(((LocalDateTime) objects.get(i + 4)).atZone(ZoneId.systemDefault()).toInstant()),
                    (int) objects.get(i + 2), (int) objects.get(i + 3), games, (int) objects.get(i)));
            games = new ArrayList<>();
        }
        return competitions;
    }

    public int getNewestId() {
        // 获取当前最新的competition_id
        return this.executeQuery(int.class,
                "select competition_id from competition order by competition_id desc",
                null).stream().findFirst().orElse(0) + 1;
    }
}
