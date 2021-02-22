package com.bonc.api.entity;

/**
 * @date 2021/2/7 9:38
 **/
public class Redis {
   private static String redisValue;
   private Redis(){}
   public static String getRedisValue(){
        return redisValue;
    }
    public static void setRedisValue(String s){
       redisValue=s;
    }
}
