package com.dhsba.service;

import com.dhsba.entity.Participant;

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
    void arrangeGames();

    /**
     * 增加参赛选手
     *
     * @param participant
     */
    void addParticipant(Participant participant);

    /**
     * 删除参赛选手
     *
     * @param number
     */
    void deleteParticipant(Object number);

    /**
     * 结束比赛完成选手比赛状态修改等，并将对局记录下来。如果比赛没有开始则取消比赛。
     */
    void endCompetition();

    /**
     * 是否满员
     *
     * @return
     */
    boolean isFull();
}
