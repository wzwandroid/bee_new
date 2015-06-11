package com.bee.admin.utils;

import java.util.Calendar;

/**
 * @author mark
 * @version 1.0
 */

public final class IdGenerator {
    private static final int maxId = 999999;
    private static int curId = 0;
    private static final Object lock = new Object();
    public static String getId() { //32
        return getTimeString() + "-" + getSeqId("#000000") + getRandomString(8);
    }

    private static String getShortId() { //25
        return getTimeString() + getSeqId("#000000") + getRandomString(2);
    }

    private static String getTimeString() { //17
        Calendar calendar = Calendar.getInstance();
        return FormatUtil.format(calendar.getTime(),"yyyyMMddHHmmssSSS");
    }

    private static String getSeqId(String formatType) {
        synchronized (lock) {
            if (curId >= maxId) {
                curId = 0;
            }
            return FormatUtil.format(++curId,formatType);
        }
    }

    private static String getRandomString(int count) {
        StringBuffer sb = new StringBuffer(count);
        for (int i = 0; i < count; i++) {
            int psd = (int) (Math.random() * (26 * 2 + 10));
            if (psd >= 26 + 10) { //a~z
                char a = (char) (psd + 97 - 10 - 26);
                sb.append(String.valueOf(a));
            } else if (psd >= 10) { //A~Z
                char a = (char) (psd + 65 - 10);
                sb.append(String.valueOf(a));
            } else { //0~9
                sb.append(String.valueOf(psd));
            }
        }
        return sb.toString();
    }
}
