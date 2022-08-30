package com.dhsba.controller;

import com.dhsba.entity.Schedule;

/**
 * 运行初始化程序以及后台运行程序
 * 后台功能：自动保存信息到数据库，自动更改比赛状态（是否结束），自动更改场地状态（到时退出占用）等
 */
public class BASystem implements Runnable {

    public static void init() {
        Schedule schedule = Schedule.getInstance(); //读取Competition以及Game
    }

    @Override
    public void run() {

    }
}
