package com.dhsba.dao;

import com.dhsba.entity.Account;

import java.sql.SQLException;
import java.util.Optional;

/**
 * 对账号密码的增，改，查功能
 */
public class AccountDao extends BaseDao {

    public int createAccount(String number, String password) throws SQLException {
        String sql = "insert into account (number, password) values (?, ?)";
        Object[] param = {number, password};
        return this.executeUpdate(sql, param);
    }

    /**
     * 得到密码
     *
     * @param number 用户账号
     * @return 返回Optional<String>封装的密码，账号不存在则返回空的Optional<String>
     */
    public Optional<String> getPassword(String number) {
        String sql = "select password from account where number = ?";
        Object[] param = {number};
        return this.executeQuery(String.class, sql, param).stream().findFirst();
    }

    public Account getAccount(String number, boolean isAdmin) {
        return new Account(number, this.getPassword(number).get(), isAdmin);
    }

    /**
     * 更改给定账户的密码
     *
     * @param number
     * @param password
     * @return
     */
    public int changePassword(String number, String password) {
        String sql = "update account set password = ? where number = ?";
        Object[] param = {password, number};
        return this.executeUpdate(sql, param);
    }
}
