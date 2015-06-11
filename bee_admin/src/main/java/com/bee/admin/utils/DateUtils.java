/*
*/
package com.bee.admin.utils;



import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jiankangjin on 2014/5/12.
 */
public abstract class DateUtils {
//    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 日期格式化规则
     */
    public static final String PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化规则
     */
    public static final String PATTERN = "yyyy-MM-dd";

    /**
     * 将日期转为字符串，格式化规则yyyy-MM-dd HH:mm:ss
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatDate(date, PATTERN_FULL);
    }

    /**
     * 将日期转为字符串
     * @param date 日期对象
     * @param pattern 格式化规则
     * @return 日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        if ((date == null) || !StringUtils.hasLength(pattern)) {
            return null;
        }


        return ThreadSafeDateParse.format(date, pattern);
    }

    /**
     * 将日期转为字符串
     * @param date 日期
     * @param pattern 格式化规则
     * @return 日期字符串
     */
    public static String formatDate(long date, String pattern) {
        if ((date <= 0L) || !StringUtils.hasLength(pattern)) {
            return null;
        }
        Date newDate = new Date(date);
        return formatDate(newDate, pattern);
    }

    /**
     * 将日期转为字符串，格式化规则yyyy-MM-dd HH:mm:ss
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String formatDate(long date) {
        return formatDate(date, PATTERN_FULL);
    }

    /**
     * 解析日期字符串
     * @param strDate 日期字符串
     * @param pattern 解析规则
     * @return 日期对象
     */
    public static Date parseDate(String strDate, String pattern) {
        if (!StringUtils.hasText(strDate) || !StringUtils.hasText(pattern)) {
            return null;
        }

        try {
            return ThreadSafeDateParse.parse(strDate, pattern);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 解析日期字符串,默认解析规则yyyy-MM-dd HH:mm:ss
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, PATTERN_FULL);
    }

    /**
     * 解析日期字符串，日期格式应该是长整数的字符串表现形式。
     * @param longStrDate 带解析的日期字符串
     * @return 返回日期
     */
    public static Date parseDateByLongStr(String longStrDate) {
        if (StringUtils.hasText(longStrDate)) {
            return new Date(Long.parseLong(longStrDate));
        }
        return null;
    }

    /**
     * 转换日期格式，比如将2013/01/01转为2013-01-01
     * @param srcDate 日期字符串
     * @param sourceFormat 原始的格式化规则
     * @param destFotrmat 转换的日期格式化规则
     * @return 转换后的日期字符串
     */
    public static String strDateSwapFormat(String srcDate, String sourceFormat, String destFotrmat) {
        Date date = parseDate(srcDate, sourceFormat);
        return formatDate(date, destFotrmat);
    }

    /**
     * 得到中文简体的日期，没有考虑繁体。
     * @param date 日期对象
     * @return 日期字符串，比如结果为2014年04月01日
     */
    public static String applyChineseDate(Date date) {
        String formatDate = formatDate(date, "yyyyMMdd");
        StringBuilder sb = new StringBuilder(formatDate);
        sb.insert(4, "年").insert(7, "月").insert(10, "日");
       /* return formatDate.substring(0, 4) + "年" +
                formatDate.substring(4, 6) + "月" +
                formatDate.substring(6, 8) + "日";*/
        return sb.toString();
    }

    /**
     * 得到中文简体的日期，没有考虑繁体。
     * @param date 日期对象
     * @return 日期字符串，比如结果为2014年04月01日
     */
    public static String applyChineseDate(long date) {
        Date formatDate = new Date(date);
        return applyChineseDate(formatDate);
    }

    /**
     * 得到中文简体的日期，没有考虑繁体。
     * @param date 日期对象
     * @return 日期字符串，比如结果为2014年04月01日 12:00:01
     */
    public static String applyFullChineseDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        StringBuilder sb = new StringBuilder(20);
        sb.append(cal.get(Calendar.YEAR)).append("年");
        sb.append(cal.get(Calendar.MONTH)).append("月");
        sb.append(cal.get(Calendar.DATE)).append("日");
        sb.append(" ");
        sb.append(cal.get(Calendar.HOUR_OF_DAY)).append(":").
                append(cal.get(Calendar.MINUTE)).append(":").append(cal.get(Calendar.SECOND));
        return sb.toString();
    }
}
