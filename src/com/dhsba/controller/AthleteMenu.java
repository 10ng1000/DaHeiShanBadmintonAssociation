package com.dhsba.controller;

import com.dhsba.common.CourtState;
import com.dhsba.entity.Athlete;
import com.dhsba.entity.Competition;
import com.dhsba.entity.CourtManager;
import com.dhsba.entity.Schedule;

import java.util.Scanner;

/**
 * 自动显示信息
 *
 * 比赛
 * 查看比赛日程
 * 查看特定比赛信息
 *  -> 查看对局详情
 *  -> 查看比赛胜负情况
 * 预定比赛
 * 取消比赛预定
 *
 * 场地
 * 查看场地情况
 * 预定场地
 * 取消场地预定
 * 释放场地
 *
 * 个人
 * 显示信息
 * 更改个人信息
 * 更改密码
 */
public class AthleteMenu {
    Scanner scanner = new Scanner(System.in);
    Athlete athlete;

    public AthleteMenu(String number, String password) {
        athlete = Athlete.loadAthlete(number); // 生成athlete对象
        System.out.println(athlete.getName());
        int next = -1;
        while (next != 0) {
            next = scanner.nextInt();
            System.out.println("0. 退出");
            System.out.println("1. 比赛");
            System.out.println("2. 场地");
            System.out.println("3. 个人");
            switch (next) {
                case 1 -> competitionMenu();
                case 2 -> courtMenu();
                case 3 -> personalMenu();
            }
        }
    }

    /**
     * 比赛
     * 查看比赛日程
     * -> 查看特定比赛信息
     * -> 查看对局详情
     * -> 查看比赛胜负情况
     * 预定比赛
     * 取消比赛预定
     */
    public void competitionMenu() {
        Schedule schedule = Schedule.getInstance();
        int next = -1;
        while (next != 0) {
            System.out.println("0. 返回");
            System.out.println("1. 比赛日程");
            System.out.println("2. 预约比赛");
            System.out.println("3. 取消预定");
            next = scanner.nextInt();
            switch (next) {
                case 1 -> {
                    System.out.println("输入编号查看详细信息,按0返回");
                    int number = scanner.nextInt();
                    if (number == 0) break;
                    schedule.showSchedule();
                    Competition competition = schedule.getCompetition(number);
                    if (competition == null) {
                        System.out.println("该ID对应的比赛不存在");
                    } else {
                        competition.showInfo();
                    }
                }
                case 2 -> {
                    System.out.println("输入比赛编号加入比赛，按0返回");
                    int number = scanner.nextInt();
                    if (number == 0) break;
                    Competition competition = schedule.getCompetition(number);
                    if (competition == null) {
                        System.out.println("该ID对应的比赛不存在");
                    } else {
                        if (athlete.SignUpCompetition(competition)) {
                            System.out.println("报名成功");
                        } else {
                            System.out.println("报名失败，可能是类别不符或比赛不可继续报名");
                        }
                    }
                }
                case 3 -> {
                    System.out.println("输入比赛编号加入比赛，按0返回");
                    int number = scanner.nextInt();
                    if (number == 0) break;
                    Competition competition = schedule.getCompetition(number);
                    if (competition == null) {
                        System.out.println("该ID对应的比赛不存在");
                    } else {
                        if (athlete.cancelCompetitionSignUp(competition)) {
                            System.out.println("取消成功");
                        } else {
                            System.out.println("取消失败，可能是比赛已开始或未报名该比赛");
                        }
                    }
                }
            }
        }
    }

    /**
     * 场地
     * 查看场地情况
     * 预定场地
     * 取消场地预定
     * 释放场地
     */
    public void courtMenu() {
        CourtManager courtManager = CourtManager.getInstance();
        int next = -1;
        while (next != 0) {
            next = scanner.nextInt();
            System.out.println("0. 返回");
            System.out.println("1. 场地列表");
            System.out.println("2. 预定场地");
            System.out.println("3. 查看预定");
            switch (next) {
                case 1 -> {
                    courtManager.showInfo();
                }
                case 2 -> {
                    System.out.println("请输入想要预定的场地号，按0返回");
                    int number = scanner.nextInt();
                    if (number == 0) break;
                    athlete.reserveCourt(number, CourtState.commonReserve);
                }
                case 3 -> {
                    System.out.println("您已预定的场地" + athlete.getReservingCourt());
                    System.out.println("请输入想要取消预定的场地号，按0返回");
                    int number = scanner.nextInt();
                    if (number == 0) break;
                    athlete.freeCourt(number);
                }
            }
        }
    }

    /**
     * 个人
     * 显示信息
     * 更改个人信息
     * 更改密码
     */
    public void personalMenu() {
        int next = -1;
        while (next != 0) {
            next = scanner.nextInt();
            System.out.println("0. 返回");
            System.out.println("1. 显示个人信息");
            System.out.println("2. 更改密码");
            switch (next) {
                case 1 -> {
                    athlete.showInfo();
                }
                case 2 -> {
                    System.out.println("请输入旧密码，按0返回");
                    String old = scanner.next();
                    if (old.equals("0")) break;
                    System.out.println("请输入新密码");
                    String newPassword = scanner.next();
                    if (athlete.changePassword(old, newPassword)) {
                        System.out.println("修改密码成功");
                    } else {
                        System.out.println("旧密码错误");
                    }
                }
            }
        }
    }
}
