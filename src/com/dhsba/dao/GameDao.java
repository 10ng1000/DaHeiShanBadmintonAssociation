package com.dhsba.dao;

import com.dhsba.common.Round;
import com.dhsba.entity.DoubleGame;
import com.dhsba.entity.Game;
import com.dhsba.entity.SingleGame;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

import static com.dhsba.entity.Game.WIN_POINT;

/**
 * 提供记录对局接口和获取对局接口
 */
public class GameDao extends BaseDao {

    /**
     * 完全完成对局后记录单打对局
     *
     * @param competitionId
     * @param athleteANum
     * @param athleteBNum
     * @param points
     * @param round
     * @return
     */
    public int createSingleGameRecord(int competitionId, String athleteANum, String athleteBNum,
                                      ArrayList<Pair<Integer, Integer>> points, String round) {
        int gameId = this.executeQuery(int.class, "select game_id from single_game_point " +
                        "union select game_id from double_game_point order by game_id desc",
                null).stream().findFirst().orElse(0) + 1; //获取新的game_id
        int ret = 0;
        String sql1 = "insert into single_game (game_id, athlete_a_num, athlete_b_num, competition_id, game_round) " +
                "values (?, ?, ?, ?, ?)";
        Object[] param1 = {gameId, athleteANum, athleteBNum, competitionId, round};
        ret += this.executeUpdate(sql1, param1);
        for (int i = 0; i < points.size(); i++) {
            String sql2 = "insert into single_game_point (game_id, game_num, a_point, b_point) values (?, ?, ?, ?)";
            Object[] param2 = {gameId, i, points.get(i).getLeft(), points.get(i).getRight()};
            ret += this.executeUpdate(sql2, param2);
        }
        return ret;
    }

    /**
     * 完全完成对局后记录双打对局
     *
     * @param competitionId
     * @param athleteANum
     * @param athleteBNum
     * @param points
     * @param round
     * @return
     */
    public int createDoubleGameRecord(int competitionId,
                                      Pair<String, String> athleteANum, Pair<String, String> athleteBNum,
                                      ArrayList<Pair<Integer, Integer>> points, String round) {
        int gameId = this.executeQuery(int.class, "select game_id from single_game_point " +
                        "union select game_id from double_game_point order by game_id desc",
                null).stream().findFirst().orElse(0) + 1;
        int ret = 0;
        String sql1 = "insert into double_game (game_id, athlete_a1_num, athlete_a2_num, " +
                " athlete_b1_num, athlete_b2_num, competition_id, game_round) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        Object[] param1 = {gameId, athleteANum.getLeft(), athleteANum.getRight(),
                athleteBNum.getLeft(), athleteBNum.getRight(), competitionId, round};
        ret += this.executeUpdate(sql1, param1);
        for (int i = 0; i < points.size(); i++) {
            String sql2 = "insert into double_game_point (game_id, game_num, a_point, b_point) values (?, ?, ?, ?)";
            Object[] param2 = {gameId, i, points.get(i).getLeft(), points.get(i).getRight()};
            ret += this.executeUpdate(sql2, param2);
        }
        return ret;
    }

    /**
     * 从数据库中读取所有对局，包括单双打
     *
     * @return
     */
    public ArrayList<Game> getSingleGames(int competitionId) {
        String sql = "select t1.game_round, t1.game_id, n1, n2, a_point, b_point, competition_id from \n" +
                "(select game_round, name n1, game_id, a_point, game_num, competition_id from single_game natural join" +
                " single_game_point join athlete on athlete_a_num = account_number) as t1 \n" +
                "join\n" +
                "(select game_round, name n2, game_id, b_point, game_num from single_game natural join" +
                " single_game_point join athlete on athlete_b_num = account_number) as t2\n" +
                "on t1.game_id = t2.game_id and t1.game_num = t2.game_num\n" +
                "where competition_id = ?";
        Object[] params = {competitionId};
        ArrayList<Object> objects = this.executeQuery(Object.class, sql, params);
        ArrayList<Game> games = new ArrayList<>();
        //System.out.println(objects);
        ArrayList<Pair<Integer, Integer>> a = null; //每一场比赛的比分
        int aWins = 0;
        int bWins = 0;//保存该比赛的胜场数
        boolean isDifferentGame = true; // 检测到新的比赛时更新，以生成下一个对象
        //如果检测到有两个胜场,则生成对象
        for (int i = 0; i < objects.size(); i += 7) {
            if (isDifferentGame) {
                a = new ArrayList<>();
                isDifferentGame = false;
            }
            int left = (int) objects.get(i + 4);
            //System.out.println(left);
            int right = (int) objects.get(i + 5);
            //System.out.println(right);
            a.add(Pair.of(left, right));
            if (left == WIN_POINT) aWins++;
            if (right == WIN_POINT) bWins++;
            if (aWins == 2 || bWins == 2) {
                //该轮比赛记录检测完毕
                games.add(new SingleGame(Round.valueOf((String) objects.get(i)), (String) objects.get(i + 2),
                        (String) objects.get(i + 3), a));
                isDifferentGame = true;
                aWins = 0;
                bWins = 0;
            }
        }
        return games;
    }

    public ArrayList<Game> getDoubleGames(int competitionId) {
        String sql = "select t1.game_round, t1.game_id, na1, na2, nb1, nb2, t1.a_point, t3.b_point, competition_id from \n" +
                "(select game_round, name na1, game_id, a_point, game_num, competition_id\n" +
                " from double_game natural join double_game_point " +
                "join athlete on athlete_a1_num = account_number) as t1 \n" +
                "join\n" +
                "(select game_round, name na2, game_id, b_point, game_num\n" +
                " from double_game natural join double_game_point " +
                "join athlete on athlete_a2_num = account_number) as t2\n" +
                "on t1.game_id = t2.game_id and t1.game_num = t2.game_num\n" +
                "join \n" +
                "(select game_round, name nb1, game_id, b_point, game_num\n" +
                " from double_game natural join double_game_point " +
                "join athlete on athlete_b1_num = account_number) as t3\n" +
                " on t2.game_id = t3.game_id and t2.game_num = t3.game_num\n" +
                "join \n" +
                "(select game_round, name nb2, game_id, b_point, game_num\n" +
                " from double_game natural join double_game_point " +
                "join athlete on athlete_b2_num = account_number) as t4\n" +
                "on t3.game_id = t4.game_id and t3.game_num = t4.game_num " +
                "where competition_id = ?";
        Object[] params = {competitionId};
        ArrayList<Object> objects = this.executeQuery(Object.class, sql, params);
        ArrayList<Game> games = new ArrayList<>();
        //System.out.println(objects);
        ArrayList<Pair<Integer, Integer>> a = null; //每一场比赛的比分
        int aWins = 0;
        int bWins = 0;//保存该比赛的胜场数
        boolean isDifferentGame = true; // 检测到新的比赛时更新，以生成下一个对象
        //如果检测到有两个胜场,则生成对象
        for (int i = 0; i < objects.size(); i += 9) {
            if (isDifferentGame) {
                a = new ArrayList<>();
                isDifferentGame = false;
            }
            int left = (int) objects.get(i + 6);
            //System.out.println(left);
            int right = (int) objects.get(i + 7);
            //System.out.println(right);
            a.add(Pair.of(left, right));
            if (left == WIN_POINT) aWins++;
            if (right == WIN_POINT) bWins++;
            if (aWins == 2 || bWins == 2) {
                //该轮比赛记录检测完毕
                games.add(new DoubleGame(Round.valueOf((String) objects.get(i)),
                        Pair.of((String) objects.get(i + 2), (String) objects.get(i + 3)),
                        Pair.of((String) objects.get(i + 4), (String) objects.get(i + 5)),
                        a));
                isDifferentGame = true;
                aWins = 0;
                bWins = 0;
            }
        }
        return games;
    }
}
