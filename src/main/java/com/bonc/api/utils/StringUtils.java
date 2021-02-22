package com.bonc.api.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StringUtils {
    public static boolean isEmpty(String str){
        return null == str || "".equals(str.trim());
    }

    public static String originOrNull(String str){
        return null == str || "".equals(str.trim()) ? null:str.trim();
    }

    /**
     * 将yyyymmddHHMMSS 转换为yyyymmddTHHMMSS
     *
     * @param value
     * @return
     */
    public static String changDateType(String value) {
        if (!isEmpty(value)) {
            return value;
        }
        return null;
    }

    public static String substringBefore(String str, String separator) {
        if (!isEmpty(str) && separator != null) {
            if (separator.isEmpty()) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1 ? str : str.substring(0, pos);
            }
        } else {
            return str;
        }
    }

    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        } else if (separator == null) {
            return "";
        } else {
            int pos = str.indexOf(separator);
            return pos == -1 ? "" : str.substring(pos + separator.length());
        }
    }

    public static String changeTime(Date date, int n) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.MINUTE, n);
        return dateFormat.format(c.getTime());
    }
}
