package com.dhsba.entity;

import com.dhsba.common.CourtState;
import com.dhsba.common.Position;
import com.dhsba.service.CourtService;

/**
 * 表示场地信息
 * 场地状态不止有两个而是有细分，在枚举类CourtState内给出
 */
public class Court implements CourtService {
    int number;//1到9
    Position position;
    CourtState courtState;

    public Court(int number, Position position) {
        this.number = number;
        this.position = position;
    }
}
