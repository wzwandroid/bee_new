/**
 *@author mark
 * date 2015-06-09
 */
package com.bee.admin.utils;


import java.math.BigDecimal;
import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: FormatUtil
 * @Description: 格式化工具类
 * @author：WangYong
 * @date：2011-8-10 下午12:37:44
 * @version：
 */
public class FormatUtil {
    public static String getDateTime(String format) {
        //yyyy-MM-dd HH:mm:ss
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(cal.getTime());
    }

    public static String format(Date date,String type) {
        DateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    public static String format(Date date) {
        return format(date,"yyyy-MM-dd");
    }

    public static String formatDataTime(Date date) {
        return format(date,"yyyy-MM-dd HH:mm:ss");
    }

    public static String format(double value,String type) {
        NumberFormat format = new DecimalFormat(type);
        return format.format(value);
    }

    public static String format(BigDecimal value,String type) {
        DecimalFormat format = new DecimalFormat(type);
        return format.format(value);
    }

    public static String format(BigDecimal value) {
        return format(value,"#,##0.00");
    }

    public static String format(double value) {
        return format(value,"#,##0.00");
    }

    public static String format(long value,String type) {
        NumberFormat format = new DecimalFormat(type);
        return format.format(value);
    }

    public static String format(long value) {
        return format(value,"#,###");
    }

    public static String formatCash(String src,int letterNum) {
        src = src.trim();
        String result = "";
        if (src.length() > letterNum) {
            return src.substring((src.length() - letterNum));
        } else {
            while (result.length() < (letterNum - src.length())) {
                result += "0";
            }
            result += src;
            return result;
        }
    }

//    public static String toBeanString(Object bean) {
//        try {
//            Map describe = BeanUtils.describe(bean);
//            return describe.toString();
//        } catch (NoSuchMethodException ex) {
//        } catch (InvocationTargetException ex) {
//        } catch (IllegalAccessException ex) {
//        }
//        return bean.toString();
//    }

    public static Date parse(String str) {
        return parse(str,"yyyy-MM-dd HH:mm:ss");
    }

    public static Date parse(String str,String dateType) {
        DateFormat format = new SimpleDateFormat(dateType);
        try {
            return format.parse(str);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String formatDateTime(String str) {
        return formatDataTime(parse(str));
    }
    /*
      public static String transDateFormat(String date, String sourceType,
      String targetType) {
     return format(parse(date, sourceType), targetType);
      }

      public static double parseDouble(String str) throws BaseException {
     try {
      return Double.parseDouble(str);
     } catch (NumberFormatException e) {
      throw new BaseException(e);
     }
      }

      public static long parseLong(String str) throws BaseException {
     return parseLong(str, 10);
      }

      public static long parseLong(String str, int radix) throws BaseException {
     try {
      return Long.parseLong(str, radix);
     } catch (NumberFormatException e) {
      throw new BaseException(e);
     }
      }

      public static int parseInt(String str) throws BaseException {
     return parseInt(str, 10);
      }

      public static int parseInt(String str, int radix) throws BaseException {
     try {
      return Integer.parseInt(str, radix);
     } catch (NumberFormatException e) {
      throw new BaseException(e);
     }
      }

      public static BigDecimal parseBigDecimal(String str) throws BaseException {
     try {
      return StringUtil.isEmpty(str) ? null : new BigDecimal(str);
     } catch (NumberFormatException e) {
      throw new BaseException(e);
     }
      }
     */
}
