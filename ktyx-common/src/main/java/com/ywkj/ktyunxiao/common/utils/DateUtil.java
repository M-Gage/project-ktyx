package com.ywkj.ktyunxiao.common.utils;

import com.ywkj.ktyunxiao.common.exception.CheckException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 商品资料接口
 *
 * @author MiaoGuoZhu
 * @date 2017/12/20 17:14
 */
public class DateUtil {
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_SIMPLE = "yyyy-MM-dd";

    /**
     * 获取2个日期的相隔分钟
     * @param d1
     * @param d2
     * @return
     */
    public static int dateBetween(Date d1, Date d2) {
        long n1 = d1.getTime();
        long n2 = d2.getTime();
        long diff = Math.abs(n1 - n2);
        diff /= (60 * 1000);
        return (int) diff;
    }
    /**
     * 所有日期格式转化成yyyyMMdd或yyyyMMddHHmmss
     * @param str
     * @return
     */
    public static String dateToNumberStr(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * yyyy-MM-dd或yyyy-MM-dd HH:mm:ss字符串转化成日期
     * @param str
     * @return
     */
    public static Date format(String str) {
        if (str.isEmpty()) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(str.length() > 10 ? FORMAT_FULL : FORMAT_SIMPLE);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            throw new CheckException("日期格式错误");
        }
    }

    /*DateFormat dFormat3 = new SimpleDateFormat("yyyyMMddHHmmss");
        formatDate = dFormat3.format(dt);
        System.out.println(formatDate);  */

    /**
     * 得到传入日期n天后的日期,如果传入日期为null，则表示当前日期n天后的日期
     * @param dt 获取当前日期
     * @param days 可以为任何整数，负数表示前days天，正数表示后days天
     * @return Date
     */
    public static Date getAddDayDate(Date dt, int days) {
        if (dt == null) {
            dt = new Date(System.currentTimeMillis());
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);
        return cal.getTime();
    }

    /**
     * 一段时间内获取每一天的数组
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> collectLocalDateDays(LocalDate startDate, LocalDate endDate) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(startDate, localDate -> localDate.plusDays(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }
    /**
     * 一段时间内获取每一月的数组
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> collectLocalDateMonths(LocalDate startDate, LocalDate endDate) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(startDate, localDate -> localDate.plusMonths(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.MONTHS.between(startDate, endDate) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }
    /**
     * 一段时间内获取每一年的数组
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> collectLocalDateYears(LocalDate startDate, LocalDate endDate) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(startDate, localDate -> localDate.plusYears(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.YEARS.between(startDate, endDate) + 1)
                // 由于最后要的是字符串，所以map转换一下
                .map(LocalDate::toString)
                // 把流收集为List
                .collect(Collectors.toList());
    }

    /**
     * Object类型转换为Date(yyyy-mm-dd)类型
     * @param objDate
     * @return
     */
    public static Date toDate(Object objDate) {
        String dateString = objDate.toString();
        if (dateString.isEmpty()) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(dateString.length() == 10 ? FORMAT_SIMPLE : FORMAT_FULL);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误:yyyy-MM-dd, str: " + dateString);
        }
    }

    /**
     * Date类型转换为String(yyyy-MM-dd)类型
     * @return
     * @throws RuntimeException
     */
    public static String format() throws RuntimeException {
        return format(new Date());
    }

    /**
     * Date类型转换为String(yyyy-MM-dd)类型
     * @param iDate
     * @return
     * @throws RuntimeException
     */
    public static String format(Date iDate) throws RuntimeException {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_SIMPLE);
        return format.format(iDate);
    }
}
