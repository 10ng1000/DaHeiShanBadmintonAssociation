package com.dhsba.service;

/**
 * 功能：
 * 更改密码
 * 登陆
 */
public interface AccountService {
    void changePassWord(String newPassword); //更改密码，此方法为通用方法
    boolean login(String account, String passWord); //登陆账号，返回是否登陆成功
}
