package com.dhsba.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public abstract class BaseDao {
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
        }
        catch(Exception e){
            System.err.println("数据库连接失败");
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
            preparedStatement =  connection.prepareStatement(preparedSQL);
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

    /**
     * 执行给定的SQL查询语句（可带参数），没有数据类型转换的保护机制
     * @param type 返回的数据类型，传入参数时使用class
     * @param preparedSQL 使用的SQL语句
     * @param param SQL语句中的参数
     * @param <T> 泛型标识符
     * @return 返回用一维数组表示
     */
    public <T> ArrayList <T> executeQuery(Class<T> type, String preparedSQL, Object[] param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        ArrayList<T> result = new ArrayList<>();
        try {
            connection = getConnect();
            preparedStatement =  connection.prepareStatement(preparedSQL);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    preparedStatement.setObject(i + 1, param[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            int cols = resultSet.getMetaData().getColumnCount();//获取查询结果的总列数
            while(resultSet.next()) { //对于每一行
                for (int i = 1; i <= cols; i++) {
                    result.add((T)resultSet.getObject(i)); //这一行有强制类型转换，没有安全保证
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnect(connection, preparedStatement, null);
        }
        return result;
    }
}
