package cn.sric.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCountUtil {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";


    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    // 实际调用方法
    public static String timeCount(Date dateTime) {
        return format(dateTime);
    }

    // 实际调用方法
    public static String timeCount(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_PATTERN);
        try {
            return format(format.parse(dateTime));
        } catch (ParseException e) {
            return null;
        }
    }

    // 时间转换
    public static String format(Date date) {
        Date currDate = new Date();
        long delta = currDate.getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }

        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
//		String currDateStr = DateUtil.parseDateToStr(currDate, DateUtil.DATE_TIME_FORMAT_YYYY年MM月DD日);
//		delta = DateUtil.parseStrToDate(currDateStr,DateUtil.DATE_TIME_FORMAT_YYYY年MM月DD日).getTime() - date.getTime();
//		if (delta < 24L * ONE_HOUR) {
//			return "昨天";
//		}
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


    public static void main(String[] args) {
        System.out.println(timeCount(new Date()));
    }
}
