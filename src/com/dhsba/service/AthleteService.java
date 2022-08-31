package com.dhsba.service;

import com.dhsba.common.CourtState;
import com.dhsba.entity.Competition;

/**
 * 功能：
 * 显示信息
 * 查询该运动员比赛日程
 * 报名比赛
 * 取消比赛报名
 * 更改个人信息
 * 更改密码
 */
public interface AthleteService {

    /**
     * 显示选手参与的比赛，其中未参与的比赛位于最后
     */
    void showCompetition();

    /**
     * 报名指定比赛
     *
     * @param competition 指定退出的比赛
     * @return 是否报名成功
     */
    boolean SignUpCompetition(Competition competition);

    /**
     * 退出指定比赛的报名
     *
     * @param competition 指定的比赛
     * @return 是否退出成功，不成功可能为：没有参与该比赛，该比赛不存在，该比赛已开始/结束。
     */
    boolean cancelCompetitionSignUp(Competition competition);

    /**
     * 预定场地
     *
     * @param number
     * @param futureState
     * @return 是否成功
     */
    boolean reserveCourt(int number, CourtState futureState);

    /**
     * 取消场地预定
     *
     * @param number
     * @return 是否成功
     */
    boolean freeCourt(int number);

    /**
     * 更改密码
     *
     * @param oldPassWord 旧密码
     * @param newPassWord 新密码
     * @return 是否成功（需要旧密码正确）
     */
    boolean changePassword(String oldPassWord, String newPassWord); //更改密码，返回是否更改成功
}
