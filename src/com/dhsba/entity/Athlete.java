package com.dhsba.entity;

import com.dhsba.common.AthleteCategory;
import com.dhsba.common.CourtState;
import com.dhsba.dao.AccountDao;
import com.dhsba.dao.AthleteDao;
import com.dhsba.dao.CompetitionDao;
import com.dhsba.service.AthleteService;
import com.dhsba.service.ShowAble;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;

/**
 * 运动员类
 * 表示运动员包括账号，个人信息在内的各种信息
 * 选手的擅长类别在PlayerCategory内给出
 */
public class Athlete implements AthleteService, ShowAble {
    AthleteDao athleteDao = new AthleteDao();
    String name;
    String gender;
    AthleteCategory athleteCategory;
    int level; //1到9，越小越高
    int competitionCount;
    int winCount;
    ArrayList<Competition> competitions;
    Account account;
    boolean isInCompetition;
    Court reservingCourt;
    String pair_number = null; //如果是双打则有pair

    /**
     * 新注册运动员
     */
    public Athlete(String name, String gender, AthleteCategory athleteCategory, int level, Account account) {
        this.name = name;
        this.gender = gender;
        this.athleteCategory = athleteCategory;
        this.level = level;
        this.account = account;
    }

    /**
     * 从数据库中读取单打运动员记录
     *
     * @param name
     * @param gender
     * @param athleteCategory
     * @param level
     * @param competitionCount
     * @param winCount
     * @param competitions
     * @param account
     */
    public Athlete(String name, String gender, AthleteCategory athleteCategory, int level, int competitionCount,
                   int winCount, ArrayList<Competition> competitions, Account account) {
        this.name = name;
        this.gender = gender;
        this.athleteCategory = athleteCategory;
        this.level = level;
        this.competitionCount = competitionCount;
        this.winCount = winCount;
        this.competitions = competitions;
        this.account = account;
    }

    /**
     * 从数据库中读取双打运动员记录
     *
     * @param name
     * @param gender
     * @param athleteCategory
     * @param level
     * @param competitionCount
     * @param winCount
     * @param competitions
     * @param account
     * @param pair_number
     */
    public Athlete(String name, String gender, AthleteCategory athleteCategory, int level,
                   int competitionCount, int winCount, ArrayList<Competition> competitions,
                   Account account, String pair_number) {
        this.name = name;
        this.gender = gender;
        this.athleteCategory = athleteCategory;
        this.level = level;
        this.competitionCount = competitionCount;
        this.winCount = winCount;
        this.competitions = competitions;
        this.account = account;
        this.pair_number = pair_number;
    }

    public static Athlete loadAthlete(String account_number) {
        AthleteDao athleteDao = new AthleteDao();
        AccountDao accountDao = new AccountDao();
        CompetitionDao competitionDao = new CompetitionDao();
        ArrayList<Object> information = athleteDao.getInformation(account_number);
        ArrayList<Competition> competitions = competitionDao.getAthleteCompetitions(account_number);
        return new Athlete((String) information.get(0), (String) information.get(1),
                AthleteCategory.valueOf((String) information.get(2)),
                (int) information.get(3), (int) information.get(4), (int) information.get(5),
                competitions, accountDao.getAccount(account_number, false), (String) information.get(7)
        );
    }

    /**
     * 显示选手个人信息
     */
    @Override
    public void showInfo() {
        System.out.println("1. 姓名：" + name);
        System.out.println("2. 性别：" + gender);
        System.out.println("3. 类别：" + athleteCategory);
        System.out.println("4. 等级：" + level);
        System.out.println("5. 总场数：" + competitionCount);
        System.out.println("6. 胜场：" + winCount);
    }

    @Override
    public void showCompetition() {
        Date now = new Date(System.currentTimeMillis());
        System.out.println("---即将进行的比赛---");
        for (Competition competition : competitions) {
            if (competition.getStartTime().after(now)) {
                System.out.println(competition);
            }
        }
        System.out.println("---当前比赛---");
        for (Competition competition : competitions) {
            if (competition.getStartTime().before(now) && !competition.isEnd()) {
                System.out.println(competition);
            }
        }
        System.out.println("---历史比赛---");
        for (Competition competition : competitions) {
            if (competition.getStartTime().before(now) && competition.isEnd()) {
                System.out.println(competition);
            }
        }
    }

    @Override
    public boolean SignUpCompetition(Competition competition) {
        if (!competition.canParticipate() || athleteCategory != competition.getType() || isInCompetition) {
            return false;
        } else {
            competitions.add(competition);
            isInCompetition = true;
            if (pair_number != null) competition.addParticipant(new Participant(this));
            else competition.addParticipant(new Participant(Pair.of(this, loadAthlete(pair_number))));
            return true;
        }
    }

    @Override
    public boolean cancelCompetitionSignUp(Competition competition) {
        if (competition.getStartTime().before(new Date(System.currentTimeMillis())) ||
                !competitions.contains(competition)) {
            return false;
        } else {
            competitions.remove(competition);
            isInCompetition = false;
            if (pair_number != null) competition.deleteParticipant(this.getAccountNumber());
            else competition.deleteParticipant((Pair.of(this.getAccountNumber(), pair_number)));
            return true;
        }
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(account.getPassword())) {
            account.changePassWord(newPassword);
        }
        return false;
    }

    public boolean reserveCourt(int number, CourtState futureState) {
        CourtManager courtManager = CourtManager.getInstance();
        Court court = courtManager.getCourt(number);
        if (court.reserveCourt(futureState)) {
            reservingCourt = court;
            return true;
        }
        return false;
    }

    public boolean freeCourt(int number) {
        CourtManager courtManager = CourtManager.getInstance();
        Court court = courtManager.getCourt(number);
        if (court.freeCourt()) {
            reservingCourt = null;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setAthleteCategory(AthleteCategory athleteCategory) {
        this.athleteCategory = athleteCategory;
        athleteDao.updateCategory(account.getAccountNumber(), athleteCategory.toString());
    }

    public void setLevel(int level) {
        this.level = level;
        athleteDao.updateLevel(account.getAccountNumber(), level);
    }

    public void setCompetitionCount(int competitionCount) {
        this.competitionCount = competitionCount;
        athleteDao.updateCompetitionCount(account.getAccountNumber(), competitionCount);
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
        athleteDao.updateWinCount(account.getAccountNumber(), winCount);
    }

    public void setInCompetition(boolean inCompetition) {
        isInCompetition = inCompetition;
    }

    public Court getReservingCourt() {
        return reservingCourt;
    }

    public void setReservingCourt(Court reservingCourt) {
        this.reservingCourt = reservingCourt;
    }

    public String getAccountNumber() {
        return account.getAccountNumber();
    }


}
