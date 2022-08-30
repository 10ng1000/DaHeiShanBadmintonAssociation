package com.dhsba.entity;

import com.dhsba.dao.AccountDao;
import com.dhsba.service.AccountService;

import java.util.Optional;

/**
 * 账号类
 * 表示账号，密码，类别（管理员或运动员）
 */
public class Account implements AccountService {
    AccountDao accountDao = new AccountDao();
    String accountNumber;
    String password;
    boolean isAdmin;//0为运动员，1为管理员

    public Account(String accountNumber, String password, boolean isAdmin) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public static boolean login(String number, String password) {
        AccountDao accountDao = new AccountDao();
        Optional<String> gotPassword = accountDao.getPassword(number);
        if (gotPassword.isEmpty()) return false;
        return gotPassword.get().equals(password);
    }

    @Override
    public void changePassWord(String newPassword) {
        password = newPassword;
        accountDao.changePassword(accountNumber, password);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
