package com.dhsba.controller;

import com.dhsba.dao.AccountDao;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * 选择登陆方式
 * 登陆
 * 注册账号
 */
public class LoginMenu {
    public LoginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎来到大黑山羽毛球协会管理程序，请选择您要进行的操作");
        while (true) {
            showWelcomeMessage();
            switch (scanner.nextInt()) {
                case 1:
                    if (login(false)) return;
                case 2:
                    if (login(true)) return;
                case 3:
                    if (signIn()) return;
            }
        }
    }

    public void showWelcomeMessage() {
        System.out.println("1. 运动员登陆");
        System.out.println("2. 管理员登陆");
        System.out.println("3. 账号注册");
    }

    /**
     * @param type 如果是0则是运动员，1则是管理员
     */
    public boolean login(boolean type) {
        Scanner scanner = new Scanner(System.in);
        String number;
        String password;
        System.out.println("请输入账号");
        number = scanner.nextLine();
        System.out.println("请输入密码");
        password = scanner.nextLine();
        if (checkLogin(number, password)) {
            if (type) new AthleteMenu();
            else new AdministratorMenu();
            return true;
        }
        System.out.println("账号或密码错误，请重新选择");
        return false;
    }

    public boolean checkLogin(String number, String password) {
        AccountDao accountDao = new AccountDao();
        return accountDao.getPassword(number).get().equals(password);
    }

    public boolean signIn() {
        Scanner scanner = new Scanner(System.in);
        AccountDao accountDao = new AccountDao();
        String number = scanner.nextLine();
        String password = scanner.nextLine();
        try {
            accountDao.createAccount(number, password);
        } catch (SQLException e) {
            System.out.println("该账号已存在，请重试");
            return false;
        }
        System.out.println("成功注册");
        return true;
    }
}
