package com.dhsba.controller;

import com.dhsba.common.AthleteCategory;
import com.dhsba.dao.AccountDao;
import com.dhsba.dao.AthleteDao;
import com.dhsba.entity.Account;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * 选择登陆方式
 * 登陆
 * 注册账号
 */
public class LoginMenu {
    Scanner scanner = new Scanner(System.in);
    public LoginMenu() {
        int next = -1;
        System.out.println("欢迎来到大黑山羽毛球协会管理程序，请选择您要进行的操作");
        while (next != 0) {
            showWelcomeMessage();
            next = scanner.nextInt();
            switch (next) {
                case 1 -> login(false);
                case 2 -> login(true);
                case 3 -> signIn();
            }
        }
    }

    public void showWelcomeMessage() {
        System.out.println("0. 退出");
        System.out.println("1. 运动员登陆");
        System.out.println("2. 管理员登陆");
        System.out.println("3. 账号注册");
    }

    public void login(boolean type) {
        String number;
        String password;
        System.out.println("请输入账号");
        number = scanner.next();
        if (type && !number.equals("root")) {
            System.out.println("这不是一个管理员账号");
            return;
        }
        System.out.println("请输入密码");
        password = scanner.next();
        if (Account.login(number, password)) {
            if (!type) new AthleteMenu(number, password);
            else new AdministratorMenu();
            return;
        }
        System.out.println("账号或密码错误");
    }

    public void signIn() {
        Scanner scanner = new Scanner(System.in);
        AccountDao accountDao = new AccountDao();
        System.out.println("请输入账号名");
        String number = scanner.next();
        System.out.println("请输入密码");
        String password = scanner.next();
        System.out.println("姓名：");
        String name = scanner.next();
        System.out.println("性别：");
        String gender = scanner.next();
        System.out.println("类别：");
        System.out.println("可选：" + AthleteCategory.allToString());
        AthleteCategory athleteCategory = AthleteCategory.valueOf(scanner.next());
        System.out.println("等级：");
        System.out.println("可选：" + "从一到九");
        int level = scanner.nextInt();
        try {
            accountDao.createAccount(number, password);
        } catch (SQLException e) {
            System.out.println("该账号已存在，请重试");
            return;
        }
        AthleteDao athleteDao = new AthleteDao();
        athleteDao.createAthlete(number, name, gender);
        athleteDao.updateLevel(number, level);
        athleteDao.updateCategory(number, athleteCategory.toString());
        athleteDao.updateWinCount(number, 0);
        athleteDao.updateCompetitionCount(number, 0);
        System.out.println("成功注册");
    }
}
