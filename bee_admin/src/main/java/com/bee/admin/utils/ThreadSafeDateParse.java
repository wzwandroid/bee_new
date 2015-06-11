/*
*/
package com.bee.admin.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程安装的日期解析和格式化类,解决JDK提供的日期格式化类无法在多线程正确处理日期。
 * Created by jiankangjin on 2014/5/12.
 */
public class ThreadSafeDateParse {
//    private static final Logger log = LoggerFactory.getLogger(ThreadSafeDateParse.class);

    private static final ThreadLocal<Map<String, DateFormat>> PARSERS = new ThreadLocal() {
        protected Map<String, DateFormat> initialValue() {
            return new HashMap<String,DateFormat>();
        }
    };

    private static final DateFormat getParser(String pattern) {
        Map parserMap = (Map) PARSERS.get();
        DateFormat df = (DateFormat) parserMap.get(pattern);
        if (df == null) {
//            log.debug("Date Format Pattern %a was not found in the current thread:%s", pattern, Thread.currentThread().getId());
            df = new SimpleDateFormat(pattern);
            parserMap.put(pattern, df);
        }
        return df;
    }

    /**
     * 解析日期字符串
     * @param srcDate 日期字符串
     * @param pattern 解析格式
     * @return 日期对象
     * @throws java.text.ParseException
     */
    public static Date parse(String srcDate, String pattern) throws ParseException {
        return getParser(pattern).parse(srcDate);
    }

    /**
     * 解析日期字符串
     * @param srcDate 日期字符串
     * @param pattern 解析格式
     * @return
     * @throws java.text.ParseException
     */
    public static long parseLongDate(String srcDate, String pattern)
            throws ParseException {
        return parse(srcDate, pattern).getTime();
    }

    /**
     * 格式化日期，转为字符串
     * @param theDate 日期
     * @param pattern 格式化规则
     * @return 日期字符串
     */
    public static String format(Date theDate, String pattern) {
        return getParser(pattern).format(theDate);
    }

    /**
     * 格式化日期，转为字符串
     * @param theDate 日期
     * @param pattern 格式化规则
     * @return
     */
    public static final String format(long theDate, String pattern) {
        return getParser(pattern).format(new Date(theDate));
    }
}
