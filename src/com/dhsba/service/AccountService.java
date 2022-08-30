package com.dhsba.service;

/**
 * 功能：
 * 更改密码
 * 登陆
 */
public interface AccountService {

    /**
     * 更改用户密码并保存更改到数据库中
     *
     * @param newPassword
     */
    void changePassWord(String newPassword);
}
