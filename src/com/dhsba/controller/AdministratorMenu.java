package com.dhsba.controller;

import com.dhsba.common.AthleteCategory;
import com.dhsba.common.CourtState;
import com.dhsba.common.Round;
import com.dhsba.dao.CompetitionDao;
import com.dhsba.entity.*;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * 管理员界面：
 * 闲置场地（选手提前离开但是忘了释放场地）
 * 特殊预约场地
 * 创建比赛
 * 对局计分
 */
public class AdministratorMenu {
    Scanner scanner = new Scanner(System.in);
    Schedule schedule = Schedule.getInstance();
    CourtManager courtManager = CourtManager.getInstance();
    CompetitionDao competitionDao = new CompetitionDao();

    public AdministratorMenu() {
        int next = -1;
        while (next != 0) {
            System.out.println("0. 退出");
            System.out.println("1. 闲置场地");
            System.out.println("2. 占用场地");
            System.out.println("3. 创建比赛");
            System.out.println("4. 对局计分");
            next = scanner.nextInt();
            switch (next) {
                case 1 -> freeCourt();
                case 2 -> reserveCourt();
                case 3 -> createCompetition();
                case 4 -> recordGame();
            }
        }
    }

    public void freeCourt() {
        System.out.println("输入号码释放场地，按0返回");
        courtManager.showInfo();
        int number = scanner.nextInt();
        if (number == 0) return;
        if (courtManager.getCourt(number).freeCourt()) {
            System.out.println("成功释放");
        } else {
            System.out.println("释放失败");
        }
    }

    public void reserveCourt() {
        System.out.println("输入号码预约场地，按0返回");
        courtManager.showInfo();
        int number = scanner.nextInt();
        if (number == 0) return;
        System.out.println("可用状态：" + CourtState.allReserveToString());
        String state = scanner.next();
        if (courtManager.getCourt(number).reserveCourt(CourtState.valueOf(state))) {
            System.out.println("成功预约");
        } else {
            System.out.println("预约失败");
        }
    }


    public void createCompetition() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println("输入相关信息，按0返回，按其他任意键继续");
        int next = scanner.nextInt();
        if (next == 0) return;
        System.out.println("类型：" + AthleteCategory.allToString());
        AthleteCategory type = AthleteCategory.valueOf(scanner.next());
        System.out.println("日期：" + "格式：yyyy-mm-dd HH:mm");
        String input1 = scanner.next();
        String input2 = scanner.next();
        Date date;
        try {
            date = simpleDateFormat.parse(input1 + " " + input2);
        } catch (ParseException e) {
            System.out.println("格式错误，请重试");
            return;
        }
        System.out.println("人数上限：");
        int registerMax = scanner.nextInt();
        schedule.addCompetition(new Competition(type, date, registerMax));
        competitionDao.createCompetitionRecord(type.toString(), registerMax, date, competitionDao.getNewestId());
        System.out.println("创建比赛成功");
    }

    public void recordGame() {
        System.out.println("选择比赛，按0返回");
        schedule.showSchedule();
        int next = scanner.nextInt();
        if (next == 0) return;
        Competition competition = schedule.getCompetition(next);
        if (!competition.isStart()) {
            System.out.println("比赛还未开始");
            return;
        }
        System.out.println("选择对局");
        ArrayList<Game> games = competition.getGames();
        for (int i = 0; i < games.size(); i++) {
            System.out.println(String.valueOf(i) + games.get(i));
        }
        while (next != 0) {
            System.out.println("输入更改的局号:");
            int number = scanner.nextInt();
            System.out.println("输入左比分:");
            int leftPoint = scanner.nextInt();
            System.out.println("输入右比分:");
            int rightPoint = scanner.nextInt();
            if (leftPoint > 21 || rightPoint > 21 || leftPoint < 0 || rightPoint < 0) {
                System.out.println("非法输入，请重试");
            } else {
                boolean end = games.get(number).changePoint(competition.getCompetitionId(), Pair.of(leftPoint, rightPoint));
                if (end) {
                    if (games.get(number).getRound() == Round.Final) {
                        competition.endCompetition();
                    } else {
                        ArrayList<Participant> participants = new ArrayList<>();
                        for (Game game : competition.getGames()) {
                            // 单打
                            participants.add(new Participant(Athlete.loadAthlete((String) game.getWinnerNumber())));
                        }
                        competition.arrangeGames(participants, competition.getGames().size());
                    }
                }
            }
            System.out.println("按0退出，按任意键继续记录");
            next = scanner.nextInt();
        }
    }
}
