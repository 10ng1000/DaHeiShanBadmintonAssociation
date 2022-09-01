package com.dhsba.controller;

import com.dhsba.common.AthleteCategory;
import com.dhsba.dao.AccountDao;
import com.dhsba.dao.AthleteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 快速向数据库中插入运动员信息
 * ！程序开始则自动插入
 */
public class QuickDataInput {
    Random random = new Random();
    AthleteDao athleteDao = new AthleteDao();
    AccountDao accountDao = new AccountDao();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<AthleteCategory> athleteCategories = new ArrayList<>();
    ArrayList<String> number = new ArrayList<>();
    ArrayList<String> password = new ArrayList<>();
    ArrayList<String> gender = new ArrayList<>();
    ArrayList<Integer> level = new ArrayList<>();

    public QuickDataInput() {
        names.addAll(Arrays.asList("黎俊迈", "梁高轩", "吕阳曜", "程英毅", "邓信厚", "陈和悦", "于鹏云", "胡思聪", "赖凯安", "孟俊发", "刘英纵",
                "杜宏浚", "尹元驹", "侯星阑", "常高轩", "高元基", "程正真", "梁志诚", "魏元正", "韩宜年", "范明诚", "徐睿德",
                "郭永春", "杜永元", "沈立辉",
                "叶东玲", "孙华楚", "杨暖姝", "邓孟乐", "孙元槐", "沈依玲", "林碧春", "郑殷漓", "沈晓瑶", "傅小萱", "陆曼语",
                "苏白莲", "胡星瑶", "崔念巧", "何山梅", "龙翠柔", "汪芷珊", "邵傲蕾", "邵淑雅", "郑绿蕊", "尹子萱", "廖千青",
                "邹佳莉", "夏以彤", "戴千柔"));
        for (int i = 0; i < 50; i++) {
            number.add(i, String.valueOf(random.nextInt(10000)));
            password.add(i, String.valueOf(random.nextInt(10000)));
            if (i < 25) {
                gender.add(i, "男");
                athleteCategories.add(i, AthleteCategory.getManType().get(i % 3));
            } else {
                gender.add(i, "女");
                athleteCategories.add(i, AthleteCategory.getWomanType().get(i % 3));
            }
            level.add(i, random.nextInt(8) + 1);
        }
    }

    public static void main(String[] args) throws SQLException {
        QuickDataInput quickDataInput = new QuickDataInput();
        System.out.println(quickDataInput);
        /**
         for (int i = 0; i <50 ; i++) {
         quickDataInput.accountDao.createAccount(quickDataInput.number.get(i),quickDataInput.password.get(i));
         quickDataInput.athleteDao.createAthlete(quickDataInput.number.get(i), quickDataInput.names.get(i),
         quickDataInput.gender.get(i),quickDataInput.athleteCategories.get(i).toString(),
         quickDataInput.level.get(i),0 ,0);
         }*/
    }

    @Override
    public String toString() {
        return "QuickDataInput{" +
                "names=" + names +
                ", athleteCategories=" + athleteCategories +
                ", number=" + number +
                ", password=" + password +
                ", gender=" + gender +
                ", level=" + level +
                '}';
    }
}
