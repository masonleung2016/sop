package sop.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:21
 * @Package: sop.util
 */

public class BaseSys {


    private static volatile long DELTA_TO_DB_TIMESTAMP = 0L;

    public static void setDbTimestampDelta(long delta) {
        DELTA_TO_DB_TIMESTAMP = delta;
    }

    public static boolean isUnix() {
        return System.getProperty("file.separator", "").equals("/");
    }

    /**
     * On UNIX systems, this character is <code>':'</code>; on Microsoft Windows systems it
     * is <code>';'</code>.
     */
    public static String getPathSeparator() {
        return File.pathSeparator;
    }

    public static String getFolderSeparator() {
        return File.separator;
    }


    public static java.util.Calendar getServerCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis() + DELTA_TO_DB_TIMESTAMP);
        return cal;
    }

    public static java.util.Date getServerDate() {
        return getServerCalendar().getTime();
    }

    public static java.util.Date getDate() {
        return getServerDate();
    }

    public static java.sql.Timestamp getTimestamp() {
        return new java.sql.Timestamp(getDate().getTime());
    }

    public static String getYYYY() {
        return String.valueOf(getYear());
    }

    public static String getMM() {
        return StringUtils.leftPad(String.valueOf(getMonth() + 1), 2, "0");
    }

    public static String getDD() {
        return StringUtils.leftPad(String.valueOf(getDay()), 2, "0");
    }

    public static int getYear() {
        return getCalendarWithoutTime().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return getCalendarWithoutTime().get(Calendar.MONTH);
    }

    public static int getDay() {
        return getCalendarWithoutTime().get(Calendar.DAY_OF_MONTH);
    }

    public static java.util.Date getDateWithoutTime() {
        return DateUtils.trimDate(getServerDate(), Calendar.DAY_OF_MONTH);
    }

    public static java.util.Calendar getCalendarWithoutTime() {
        return DateUtils.trimDate(getServerCalendar(), Calendar.DAY_OF_MONTH);
    }


    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(getServerDate());
    }

    public static java.sql.Timestamp getTimestampWithoutMillisecond() {
        long sysTime = getTimestamp().getTime();
        return new java.sql.Timestamp((sysTime / 1000) * 1000);
    }

    public static void main(String[] args) {

        System.out.println(getYear());
        System.out.println(getMonth());
        System.out.println(getDay());


        System.out.println(getYYYY());
        System.out.println(getMM());
        System.out.println(getDD());
    }
}
