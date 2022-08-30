package com.dhsba.service;

import com.dhsba.entity.Participant;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 功能：
 * 对局计分
 * 对局结算
 * 显示对局详情
 */
public interface GameService {
    /**
     * 更改该轮比赛未记录成绩的下一场的运动员的得分，在球员得分达到胜利条件时修改运动员在该轮比赛的胜场，如果该运动员达到两胜则晋级
     *
     * @param newPoint 本场比分，按照显示的运动员顺序填写比分
     * @return 比赛是否结束
     */
    boolean changePoint(Pair<Integer, Integer> newPoint);

    /**
     * 保存对局到数据库
     *
     * @return
     */
    boolean saveGame(int competitionId, Participant athleteA, Participant athleteB);
}
