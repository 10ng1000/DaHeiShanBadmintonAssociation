package com.dhsba.service;

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
    void showInfo(); //显示选手个人信息
    void showMyCompetition(); //显示选手参与的比赛，其中未参与的比赛位于最后
    boolean SignUpCompetition(Competition competition); //报名指定比赛，返回是否报名成功
    boolean cancelCompetitionSignUp(Competition competition); //退出指定比赛的报名，返回是否成功
    void changeInfo(String InfoName, String newInfo); //更改个人信息，参数为字符串
    boolean changePassWord(String oldPassWord, String newPassWord); //更改密码，返回是否更改成功
}
