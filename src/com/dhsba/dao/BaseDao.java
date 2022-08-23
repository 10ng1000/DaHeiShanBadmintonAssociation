package com.dhsba.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    public static String DRIVER;
    public static String URL;
    public static String DBNAME;
    public static String DBPASSWORD;

    static {
        init();
    }

    //从.properties中读取数据，初始化配置和成员变量
    public static void init(){
        Properties params = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("database.properties");
        try {
            params.load(is);
        }catch (IOException e){
            e.printStackTrace();
        }
        DRIVER = params.getProperty("driver");
        URL = params.getProperty("url");
        DBNAME = params.getProperty("user");
        DBPASSWORD = params.getProperty("password");
    }

    //使用配置文件中的数据初始化连接
    public Connection getConnect(){
        Connection connection = null;
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,DBNAME,DBPASSWORD);
            System.out.println("连接成功！");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    //关闭连接
    public void closeConnect(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //执行给定的SQL更新语句（可带参数）
    public int executeUpdate(String preparedSQL, Object[] param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try {
            connection = getConnect();
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    preparedStatement.setObject(i + 1, param[i]);
                }
            }
            num = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection, preparedStatement, null);
        }
        return num;
    }

    //执行给定的SQL查询语句（可带参数）
    public ResultSet executeQuery(String preparedSQL, Object[] param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnect();
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    preparedStatement.setObject(i + 1, param[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection, preparedStatement, null);
        }
        return resultSet;
    }
}
