package com.dhsba.entity;

import com.dhsba.common.AthleteCategory;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 抽象意义的比赛参与者，方便Competition的使用而不用区别是单打运动员还是双打运动员
 * 一个单打运动员和一对双打运动员都是一个参与者
 */
public class Participant {
    Object athlete;
    AthleteCategory athleteCategory;

    public Participant(Athlete a) {
        athlete = a;
        athleteCategory = a.athleteCategory;
    }

    public Participant(Pair<Athlete, Athlete> a) {
        athlete = a;
        athleteCategory = a.getLeft().athleteCategory;
    }

    /**
     * 返回运动员
     *
     * @return 如果是单打运动员则返回一个Athlete， 如果是双打运动员则返回一个Pair<Athlete, Athlete>
     */
    public Object getAthlete() {
        return athlete;
    }

    /**
     * 返回运动员账号
     *
     * @return 如果是单打运动员则返回String， 如果是双打运动员则返回Pair<String, String>
     */
    public Object getAccountNumber() {
        if (athleteCategory.isSingle()) {
            Athlete athlete = (Athlete) this.athlete;
            return athlete.getAccountNumber();
        } else {
            Pair<Athlete, Athlete> athlete = (Pair<Athlete, Athlete>) this.athlete;
            return Pair.of(athlete.getLeft().getAccountNumber(), athlete.getRight().getAccountNumber());
        }
    }
}
