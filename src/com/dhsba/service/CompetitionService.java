package com.dhsba.service;

import com.dhsba.entity.Participant;

import java.util.ArrayList;

/**
 * 功能：
 * 显示所有比赛日程
 * 显示比赛信息
 * 安排对局
 * 更新比赛人数
 * 取消比赛
 */
public interface CompetitionService {
    /**
     * 显示比赛对局情况
     */
    void showGameInfo();

    /**
     * 创建数个对局，并组织好对局选手
     * 只有选手是偶数时可以开始比赛
     * 普通的晋级赛机制，没有种子选手等等
     */
    void arrangeGames(ArrayList<Participant> participantArrayList, int count);

    /**
     * 是否能够支持运动员继续参加比赛
     *
     * @return
     */
    boolean canParticipate();

    /**
     * 增加参赛选手并写入数据库
     * !controller层不要调用该方法
     *
     * @param participant
     */
    void addParticipant(Participant participant);

    /**
     * 删除参赛选手并同步数据库
     * !controller层不要调用该方法
     *
     * @param number
     */
    void deleteParticipant(Object number);

    /**
     * 结束比赛完成选手比赛状态修改等，并将对局记录到数据库。如果比赛没有开始则取消比赛。
     */
    void endCompetition();
}
