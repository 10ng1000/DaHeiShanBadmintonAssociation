package com.dhsba.entity;

import com.dhsba.common.AthleteCategory;
import com.dhsba.service.AthleteService;

import java.util.ArrayList;

/**
 * 运动员类
 * 表示运动员包括账号，个人信息在内的各种信息
 * 选手的擅长类别在PlayerCategory内给出
 */
public class Athlete implements AthleteService {
    String name;
    String gender;
    AthleteCategory athleteCategory;
    int level; //1到9，越小越高
    int competitionCount;
    int winCount;
    ArrayList<Competition> schedule;
    ArrayList<Competition> competitionRecord;
    Account account;
    boolean isInCompetition;
    boolean reservingCourt;

    public Athlete(String name, String gender, AthleteCategory athleteCategory, int level) {
        this.name = name;
        this.gender = gender;
        this.athleteCategory = athleteCategory;
        this.level = level;
    }

    @Override
    public void showInfo() {

    }

    @Override
    public void showMyCompetition() {

    }

    @Override
    public boolean SignUpCompetition(Competition competition) {
        return false;
    }

    @Override
    public boolean cancelCompetitionSignUp(Competition competition) {
        return false;
    }

    @Override
    public void changeInfo(String InfoName, String newInfo) {

    }

    @Override
    public boolean changePassWord(String oldPassWord, String newPassWord) {
        return false;
    }
}
