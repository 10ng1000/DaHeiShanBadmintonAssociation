package com.dhsba.entity;

import com.dhsba.service.AccountService;

/**
 * 账号类
 * 表示账号，密码，类别（管理员或运动员）
 */
public class Account implements AccountService {
    String accountNumber;
    String passWord;
    boolean type;//0为运动员，1为管理员

    public Account(String accountNumber, String passWord, boolean type) {
        this.accountNumber = accountNumber;
        this.passWord = passWord;
        this.type = type;
    }
}
