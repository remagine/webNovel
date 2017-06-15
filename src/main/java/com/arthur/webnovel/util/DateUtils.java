package com.arthur.webnovel.util;

import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMMDDHHMM_INLINE = "yyyyMMddHHmm";

    public static final String YYYYMMDDHHMMSS = "yyyyMMDDHHmmss"; // 포스용

    public static Date parse(String source) {
        return parse(source, DEFAULT_DATE_FORMAT);
    }

    public static Date parse(String source, String f) {
        Date date = null;
        if (StringUtils.isNotEmpty(source)) {
            try {
                Format format = FastDateFormat.getInstance(f, Locale.getDefault());
                date = (Date) format.parseObject(source);
            } catch (ParseException e) {
            }
        }
        return date;
    }

    public static String format(Date d) {
        return format(d, DEFAULT_DATE_FORMAT);
    }

    public static String format(Date d, String f) {
        if (d != null) {
            Format format = FastDateFormat.getInstance(f, Locale.getDefault());
            return format.format(d);
        }
        return null;
    }

    public static boolean betweenOrSame(Date startDate, Date endDate) {

        Date point = new Date(); // default now
        return betweenOrSame(startDate, endDate, point);
    }

    public static boolean betweenOrSame(Date startDate, Date endDate, Date point) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(point, startDate) || org.apache.commons.lang3.time.DateUtils.isSameDay(point, endDate)
                || (point.after(startDate) && point.after(endDate));
    }

    public static Date beforeDay(Date ref, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ref);
        cal.add(Calendar.DATE, -days);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date afterMonth(Date ref, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ref);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date afterDay(Date ref, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ref);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date startOfDay(Date ref) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ref);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date endOfDay(Date ref) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ref);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.add(Calendar.DATE, 1);

        return cal.getTime();
    }

    public static Date endOfYear(Date ref) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startOfDay(ref));

        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DATE, 31);

        return cal.getTime();
    }

    public static Date firstDay(Date ref) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startOfDay(ref));
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static Long dayDiff(Date to, Date from) {
        if (to != null && from != null) {
            long dateTimeTo = to.getTime();
            long dateTimeForm = from.getTime();
            long dayDiff = ( dateTimeTo - dateTimeForm) / (1000 * 60 * 60 * 24);
            return dayDiff;
        }
        return null;
    }

}
