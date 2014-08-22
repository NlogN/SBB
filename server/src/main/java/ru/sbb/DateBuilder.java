package ru.sbb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ilya Makeev
 * DateBuilder: 21.08.14
 */
public class DateBuilder {

    public static long getCurrentTime() {
        java.util.Date date = new java.util.Date();
        return date.getTime() / 1000;
    }

    public static long getUnixTime(java.util.Date date) {
        return date.getTime() / 1000;
    }

    public static java.util.Date createDate(int year, int month, int day, int hourofday, int minute, int second) {
        if (day == 0 && month == 0 && year == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hourofday, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static java.util.Date createDateTime(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(s);
        return date;
    }

    public static java.util.Date createDate(int year, int month, int day) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        java.util.Date utilDate = null;
//        try {
//            utilDate = formatter.parse(year + "/" + month + "/" + day);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return createDate(year,month,day,0,0,0);
    }


}
