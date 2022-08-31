package com.dhsba.entity;

import com.dhsba.common.Position;
import com.dhsba.service.ShowAble;

import java.util.ArrayList;

public class CourtManager implements ShowAble {
    public static final long commonReserveTime = 60 * 60 * 1000; //普通预约时长（毫秒）
    private final static CourtManager courtManager = new CourtManager();
    ArrayList<Court> courts = new ArrayList<>();

    private CourtManager() {
        courts.add(new Court(1, Position.east));
        courts.add(new Court(2, Position.east));
        courts.add(new Court(3, Position.south));
        courts.add(new Court(4, Position.west));
        courts.add(new Court(5, Position.west));
        courts.add(new Court(6, Position.west));
        courts.add(new Court(7, Position.north));
        courts.add(new Court(8, Position.north));
        courts.add(new Court(9, Position.north));
    }

    public static CourtManager getInstance() {
        return courtManager;
    }

    public ArrayList<Court> getCourts() {
        return courts;
    }

    public Court getCourt(int number) {
        return courts.get(number + 1);
    }

    @Override
    public void showInfo() {
        for (Court court : courts) court.showInfo();
    }
}
