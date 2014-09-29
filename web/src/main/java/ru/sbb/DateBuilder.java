package ru.sbb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 */
public class DateBuilder {

    public static long getCurrentTime() {
        Date date = new Date();
        return date.getTime() / 1000;
    }

    public static long getUnixTime(Date date) {
        return date.getTime() / 1000;
    }

    public static Date createDate(int year, int month, int day, int hourofday, int minute, int second) {
        if (day == 0 && month == 0 && year == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hourofday, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date createDateTime(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(s);
        return date;
    }

    public static Date createDate(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(s);
        return date;
    }

    public static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0, 0);
    }

    public static String dateToString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String dateTimeToString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }


}
