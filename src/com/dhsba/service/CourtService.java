package com.dhsba.service;

import com.dhsba.common.CourtState;

/**
 * 功能：
 * 显示场地状态
 * 预定场地
 * 释放场地
 */
public interface CourtService {
    void showInfo();//显示场地位置以及预约状态;
    boolean reserveCourt(int number, CourtState futureState); //以不同状态（如一般预定，训练预定等）预约指定号码的场地
    void freeCourt(int number);
}
