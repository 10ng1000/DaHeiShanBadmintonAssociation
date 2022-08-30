package com.dhsba.entity;

import com.dhsba.common.CourtState;
import com.dhsba.common.Position;
import com.dhsba.service.CourtService;
import com.dhsba.service.ShowAble;

/**
 * 表示场地信息
 * 场地状态不止有两个而是有细分，在枚举类CourtState内给出
 */
public class Court implements CourtService, ShowAble {
    int number;
    Position position;
    CourtState courtState = CourtState.available;

    public Court(int number, Position position) {
        this.number = number;
        this.position = position;
    }

    /**
     * 显示场地位置以及预约状态;
     */
    @Override
    public void showInfo() {
        System.out.println("场地编号：" + number);
        System.out.println("所在位置：" + position);
        System.out.println("预约状态：" + courtState.toString());
    }

    @Override
    public boolean reserveCourt(int number, CourtState futureState) {
        if (courtState == CourtState.available) {
            courtState = futureState;
            return true;
        }
        return false;
    }

    @Override
    public boolean freeCourt(int number) {
        if (courtState == CourtState.available) {
            return false;
        }
        courtState = CourtState.available;
        return true;
    }
}
