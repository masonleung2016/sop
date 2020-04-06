package sop.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:23
 * @Package: sop.util
 */

public class Sys extends BaseSys {

    private Sys() {
    }

    public static Sys getInstance() {
        return new Sys();
    }

    public static Date getFirstDateOfLastMonth() {
        Calendar cal = getCalendarWithoutTime();
        if (cal.get(Calendar.MONTH) > Calendar.JANUARY) {
            return getFirstDateOfMonth(cal, cal.get(Calendar.MONTH) - 1);
        } else {
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
            return getFirstDateOfMonth(cal, Calendar.DECEMBER);
        }
    }

    public static Date getFirstDateOfThisMonth() {
        Calendar cal = getCalendarWithoutTime();
        return getFirstDateOfMonth(cal, cal.get(Calendar.MONTH));
    }

    public static Date getFirstDateOfMonth(Calendar cal, int month) {
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getMaxDate() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.YEAR, 9999);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        return DateUtils.trimDate(cal.getTime(), Calendar.DAY_OF_MONTH);
    }

    public static InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("unknown host!");
        }
        return null;
    }

    public static String getServerId() {
        InetAddress netAddress = getInetAddress();
        return getHostIp(netAddress);
    }

    public static void main(String[] args) {
        Calendar cal = getCalendarWithoutTime();

        System.out.println("getFirstDateOfLastMonth:" + DateUtils.formatDate(getFirstDateOfLastMonth()));
        System.out.println("getFirstDateOfThisMonth:" + DateUtils.formatDate(getFirstDateOfThisMonth()));
        System.out.println("getFirstDateOfMonth:" + DateUtils.formatDate(getFirstDateOfMonth(cal, 11)));
        InetAddress netAddress = getInetAddress();
        System.out.println("host ip:" + getHostIp(netAddress));
        System.out.println("host name:" + getHostName(netAddress));
        Properties properties = System.getProperties();
        Set<String> set = properties.stringPropertyNames();
        // 获取java虚拟机和系统的信息。
        for (String name : set) {
            System.out.println(name + ":" + properties.getProperty(name));
        }
    }

    private static String getHostIp(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String ip = netAddress.getHostAddress();
        // get the ip address
        return ip;
    }

    private static String getHostName(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String name = netAddress.getHostName();
        // get the host address
        return name;
    }
}
