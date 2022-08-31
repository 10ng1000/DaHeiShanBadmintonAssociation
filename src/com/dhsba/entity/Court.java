package com.dhsba.entity;

import com.dhsba.common.CourtState;
import com.dhsba.common.Position;
import com.dhsba.service.CourtService;
import com.dhsba.service.ShowAble;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 表示场地信息
 * 场地状态不止有两个而是有细分，在枚举类CourtState内给出
 */
public class Court implements CourtService, ShowAble, Comparable<Court> {
    int number;
    Position position;
    CourtState courtState = CourtState.available;
    Athlete athlete;

    @Override
    public String toString() {
        return "Court{" +
                "number=" + number +
                ", position=" + position +
                ", courtState=" + courtState +
                '}';
    }

    public Court(int number, Position position) {
        this.number = number;
        this.position = position;
    }

    /**
     * 显示场地位置以及预约状态;
     */
    @Override
    public void showInfo() {
        System.out.println("场地编号：" + number + "所在位置：" + position + "预约状态：" + courtState.toString());
    }

    @Override
    public boolean reserveCourt(CourtState futureState) {
        if (courtState == CourtState.available) {
            courtState = futureState;
            if (futureState == CourtState.commonReserve) {
                Timer timer = new Timer();
                CourtTimerTask courtTimerTask = new CourtTimerTask();
                timer.schedule(courtTimerTask, CourtManager.commonReserveTime);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean freeCourt() {
        if (courtState == CourtState.available) {
            return false;
        }
        courtState = CourtState.available;
        return true;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(Court o) {
        return this.number - o.number;
    }

    class CourtTimerTask extends TimerTask {
        public void run() {
            freeCourt();
        }
    }
}
