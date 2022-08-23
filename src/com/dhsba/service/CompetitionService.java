package com.dhsba.service;

/**
 * 功能：
 * 显示所有比赛日程
 * 显示比赛信息
 * 安排对局
 * 更新比赛人数
 * 取消比赛
 */
public interface CompetitionService {
    void showInfo(); //展示这个比赛的相关信息，若是比赛已开始则显示对局情况
    static void showSchedule(){} //显示所有比赛，其中已结束的比赛放到最后显示
    void arrangeGames(); //创建数个对局，并组织好对局选手
    void modifyCompetitorNumber(int number); //更改参与比赛的人数，这个人数即为数字，负数代表减少
    void endCompetition();//结束比赛完成选手比赛状态修改等，并将对局记录下来。如果比赛没有开始则取消比赛。
}
