package com.dhsba.service;

import com.dhsba.entity.Athlete;

/**
 * 功能：
 * 对局计分
 * 对局结算
 * 显示对局详情
 */
public interface GameService {
    /**
     * 更改该轮比赛指定（第几）场指定运动员的得分，在球员得分达到胜利条件时修改运动员在该轮比赛的胜场，如果该运动员达到两胜则晋级
     */
    void changePoint(Athlete athlete, int gameNumber);
    void showInfo();// 显示选手比分
}
