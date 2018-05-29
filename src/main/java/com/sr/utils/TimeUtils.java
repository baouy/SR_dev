package com.sr.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by DavidWang on 16/8/11.
 */
public class TimeUtils {

    public static String getCodingDate(){

        String date = null;
        Calendar calendar = new GregorianCalendar();
        String YEAR = String.valueOf(calendar.get(Calendar.YEAR));
        String MONTH = String.format("%02d",calendar.get(Calendar.MONTH) + 1);
        String DAY_OF_MONTH = String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));

        YEAR = YEAR.substring(YEAR.length() - 2,YEAR.length());
        date = YEAR+MONTH+DAY_OF_MONTH;

        return date;
    }

    public static String getNewCodingDate(){

        String date = null;
        Calendar calendar = new GregorianCalendar();
        String YEAR = String.valueOf(calendar.get(Calendar.YEAR));
        String MONTH = String.format("%02d",calendar.get(Calendar.MONTH) + 1);

        YEAR = YEAR.substring(YEAR.length() - 2,YEAR.length());
        date = YEAR+MONTH;

        return date;
    }
    public static String getCodingDateYear(){

        String date = null;
        Calendar calendar = new GregorianCalendar();
        String YEAR = String.valueOf(calendar.get(Calendar.YEAR));

        YEAR = YEAR.substring(YEAR.length() - 2,YEAR.length());
        date = YEAR;

        return date;
    }


    public static String getMDate(){
        Calendar calendar = new GregorianCalendar();
        String YEAR = String.valueOf(calendar.get(Calendar.YEAR));
        return YEAR;
    }



    /**
     * 获取当月第一天0点时间字符串
     * @return
     */
    public static String getCurrentMounth0String(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当月最后一天23时59分59秒时间字符串
     * @return
     */
    public static String getCurrentMounth1String(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getCurrentMounth1String());
    }


}
