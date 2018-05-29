package com.sr.core;

/**
 * Created by KingZS on 2018/5/28
 */
public class Constants {

    public static String REDIS_PASSWORD;

    public static String CONTENT_DEFATE;

    public static String REDIS_HOST;

    public static Integer REDIS_PORT;

    public static Integer REDIS_TIMEOUR;


    public final static  String sztz="sztz";

    public static String getRedisPassword() {
        return REDIS_PASSWORD;
    }

    public static void setRedisPassword(String redisPassword) {
        REDIS_PASSWORD = redisPassword;
    }

    public static String getContentDefate() {
        return CONTENT_DEFATE;
    }

    public static void setContentDefate(String contentDefate) {
        CONTENT_DEFATE = contentDefate;
    }

    public static String getRedisHost() {
        return REDIS_HOST;
    }

    public static void setRedisHost(String redisHost) {
        REDIS_HOST = redisHost;
    }

    public static Integer getRedisPort() {
        return REDIS_PORT;
    }

    public static void setRedisPort(Integer redisPort) {
        REDIS_PORT = redisPort;
    }

    public static Integer getRedisTimeour() {
        return REDIS_TIMEOUR;
    }

    public static void setRedisTimeour(Integer redisTimeour) {
        REDIS_TIMEOUR = redisTimeour;
    }
}
