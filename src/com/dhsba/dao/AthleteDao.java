package com.dhsba.dao;

import java.util.ArrayList;

/**
 * 对运动员的
 *  账号
 *  类别（单打双打
 *  总场数
 *  性别
 *  等级
 *  姓名
 * 进行增删改查
 */
public class AthleteDao extends BaseDao{

    /**
     * 向数据库写入运动员基础信息，由account_number唯一决定
     *
     * @param accountNumber
     * @param name
     * @param gender
     * @return
     */
    public int createAthlete(String accountNumber, String name, String gender, String category, int level,
                             int winCount, int competitionCount) {
        String sql = "insert into athlete (account_number, name, gender, category," +
                " level, win_count, competition_count) values (?, ?, ?, ?, ?, ?, ?)";
        Object[] param = {accountNumber, name, gender, category, level, winCount, competitionCount};
        return this.executeUpdate(sql, param);
    }

    /**
     * 查询运动员的所有信息
     *
     * @param accountNumber 使用运动员id进行查询
     * @return 列表中依次为： name, gender, category, level, competition_count, win_count, account_number, pair_number
     */
    public ArrayList<Object> getInformation(String accountNumber) {
        String sql = "select name, gender, category, level, competition_count,win_count, account_number, pair_number" +
                " from account join athlete on number = account_number where account_number = ?";
        Object[] param = {accountNumber};
        return this.executeQuery(Object.class, sql, param);
    }

    /**
     * 更新运动员的类别，以下同理
     * @param accountNumber
     * @param newValue
     * @return
     */
    public int updateCategory(String accountNumber, String newValue) {
        String sql = "update athlete set category = ? where account_number = ?";
        Object[] param = {newValue, accountNumber};
        return this.executeUpdate(sql, param);
    }

    public int updateLevel(String accountNumber, int newValue) {
        String sql = "update athlete set level = ? where account_number = ?";
        Object[] param = {newValue, accountNumber};
        return this.executeUpdate(sql, param);
    }

    public int updateCompetitionCount(String accountNumber, int newValue) {
        String sql = "update athlete set competition_count = ? where account_number = ?";
        Object[] param = {newValue, accountNumber};
        return this.executeUpdate(sql, param);
    }

    public int updateWinCount(String accountNumber, int newValue) {
        String sql = "update athlete set win_count = ? where account_number = ?";
        Object[] param = {newValue, accountNumber};
        return this.executeUpdate(sql, param);
    }

    /**
     * 删除运动员参加比赛的记录
     *
     * @param accountNumber
     * @param oldValue
     * @return
     */
    public int deleteCompetitionRecord(String accountNumber, String oldValue) {
        String sql = "delete from athlete_in_competition where athlete_number = ? and competition_id = ?";
        Object[] param = {accountNumber, oldValue};
        return this.executeUpdate(sql, param);
    }

    /**
     * 同时更新双方的pair
     *
     * @param accountNumber
     * @param pairNumber
     * @return
     */
    public int updatePair(String accountNumber, String pairNumber) {
        String sql = "update athlete set pair = ? where account_number = ?";
        Object[] param = {pairNumber, accountNumber};
        Object[] param2 = {accountNumber, pairNumber};
        return this.executeUpdate(sql, param) + this.executeUpdate(sql, param2);
    }
}
