package com.sr.core;

import com.sr.utils.TimeUtils;

/**
 * 编号生成类
 * Created by KingZS on 2018/5/28
 */
public class CounterManage {


    //素质拓展申请记录编号
    public static String CUCounter(){
        RedisOperationManager redisOperationManager = RedisOperationManager.init();
        int count = redisOperationManager.getInt(Constants.sztz) + 1;
        redisOperationManager.setDate(Constants.sztz,count);
        return "CU"+ TimeUtils.getCodingDate()+String.format("%06d",count);
    }

}
