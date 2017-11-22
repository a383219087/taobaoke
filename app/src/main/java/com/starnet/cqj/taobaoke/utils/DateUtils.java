package com.starnet.cqj.taobaoke.utils;

import com.starnet.cqj.taobaoke.remote.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Administrator on 2017/11/22.
 */

public class DateUtils {

    public static final String PATTERN = "yyyy-MM-dd";

    public static String getToday() {
        return new SimpleDateFormat(PATTERN, Locale.getDefault()).format(new Date());
    }

    public static boolean is10DayLater(String date) {
        try {
            Date parse = new SimpleDateFormat(PATTERN, Locale.getDefault()).parse(date);
            GregorianCalendar cal1 = new GregorianCalendar();
            GregorianCalendar cal2 = new GregorianCalendar();
            cal1.setTime(parse);
            cal2.setTime(new Date());
            double dayCount = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);//从间隔毫秒变成间隔天数
            return dayCount >= Constant.TOKEN_REFRESH_DAY;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
