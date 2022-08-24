package com.dhsba.dao;

import com.dhsba.dao.BaseDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 对账号密码的增，改，查功能
 */
public class AccountDao extends BaseDao{

    public int createAccount(String number, String password) {
        String sql = "insert into account values (?, ?)";
        Object[] param = {number, password};
        return this.executeUpdate(sql,param);
    }

    public ArrayList<String> getAccount(String number){
        String sql = "select * from account where number = ?";
        Object[] param = {number};
        return this.executeQuery(String.class,sql,param);
    }

    public int changePassword(String number, String password) {
        String sql = "update account set password = ? where number = ?";
        Object[] param = {number, password};
        return this.executeUpdate(sql, param);
    }
}
