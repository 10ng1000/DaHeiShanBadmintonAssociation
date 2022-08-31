package com.dhsba.service;

import com.dhsba.common.CourtState;

/**
 * 功能：
 * 显示场地状态
 * 预定场地
 * 释放场地
 */
public interface CourtService {
    /**
     * 以不同状态（如一般预定，训练预定等）预约指定号码的场地
     *
     * @param futureState
     * @return 是否预约成功
     */
    boolean reserveCourt(CourtState futureState);

    /**
     * 释放场地，如果场地为空则返回释放失败
     *
     * @return 是否释放成功
     */
    boolean freeCourt();
}
