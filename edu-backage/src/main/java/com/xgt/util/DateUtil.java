package com.xgt.util;

import com.xgt.constant.SystemConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Author CC
 * Date: 2016年3月29日
 * Desc: 日期工具类
 */
public class DateUtil {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSSS = "yyyyMMddHHmmsss";

    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmsssss";

    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY_MM_DD_CHINESE = "yyyy 年 MM 月 dd 日 ";

    /**
     * 根据输入格式获取日期字符串
     *
     * @return
     */
    public static String getStringDate() {
        return getStringDate("yyyy-MM-dd");
    }

    public static String getStringDate(String format) {
        return getStringDate(new Date(), format);
    }

    public static String getStringDate(Date d, String f) {
        if (d == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        return sdf.format(d);
    }

    /**
     * 根据秒数获取时间
     *
     * @param date date
     * @return date
     */
    public static Long getTimeStamp(Date date) {
        if (date != null) {
            return date.getTime() / 1000;
        }
        return null;
    }


    /**
     * 根据秒数获取时间
     *
     * @param time 秒数
     * @return date
     */
    public static Date getTime(Long time) {
        if (time != null) {
            long Millis = Long.parseLong(time + "000");
            return new Date(Millis);
        }
        return null;
    }


    /**
     * 根据秒数获取时间
     *
     * @param millisTime 毫秒数
     * @return date
     */
    public static Date millisTime2Date(Long millisTime) {
        if (millisTime != null) {
            return new Date(millisTime);
        }
        return null;
    }

    /**
     * 获得最近minute的开始时间
     * 如hour=0表示本小时的开始时间，如minute=-1表示上个分钟的开始时间，如minute=1表示下个分钟的开始时间
     *
     * @return Date
     */
    public static Date getCurrentHourStartTime(int minute) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minute);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得最近hour的开始时间
     * 如hour=0表示本小时的开始时间，如date=-1表示前一天的开始时间，如date=1表示下一天的开始时间
     *
     * @return Date
     */
    public static Date getCurrentDayStartTime(Date date, int day) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date now = null;
        try {
            c.add(Calendar.DATE, day);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取传入日期相差天数
     *
     * @param start
     * @param end
     * @return
     */
    public static int diffDay(Date start, Date end) {
        return (int) ((
                DateUtils.truncate(end, Calendar.DAY_OF_MONTH).getTime() -
                        DateUtils.truncate(start, Calendar.DAY_OF_MONTH).getTime()
        ) / 86400000);
    }

    /**
     * @Description 获取当前时间
     * @author HeLiu
     * @date 2018/7/6 17:30
     */
    public static String getCurrentTime() {
        Date date = new Date();
        DateFormat d2 = DateFormat.getDateTimeInstance();
        String time = d2.format(date);
        return time;
    }

    /**
     * 格式化日期
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date formatDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        if (!StringUtils.isEmpty(dateStr)) {
            return new Date(df.parse(dateStr).getTime());
        }
        return null;
    }

    public static boolean getBeforeDate(String fdate, String ldate)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = sdf.parse(fdate);
        Date date2 = sdf.parse(ldate);
        return date1.before(date2);
    }

    /**
     * 获取传入日期的周
     *
     * @param date  当前日期
     * @param index 1.代表要获得周一 ，以此类推
     * @return
     */
    public static String getWeekDayTime(Date date, Integer index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + (index - 1));// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        return sdf.format(cal.getTime());
    }

    /*public static void main(String[] args) throws ParseException {
        *//*System.out.println(DateUtil.diffDay(new Date(), DateUtils.parseDate("2016-08-25 23:00:00", "yyyy-MM-dd HH:mm:ss")));
        System.out.println("time=="+DateUtil.getCurrentTime());*//*
        System.out.println(getCurrentTimeStr(YYYYMMDDHHMMSS));
    }*/

    /**
     * 获取传入日期的周所有日期
     *
     * @param date 当前日期
     * @return
     */
    public static List<String> getWeekTimeList(Date date) {
        List<String> timeList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        timeList.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        timeList.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        timeList.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        timeList.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        timeList.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        timeList.add(sdf.format(cal.getTime()));
        cal.add(Calendar.DATE, 1);
        timeList.add(sdf.format(cal.getTime()));
        return timeList;
    }

    public static String getMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        //当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();
        return datef.format(beginTime) + " 00:00:00";
    }

    public static String getMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        //当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();
        return datef.format(endTime) + " 23:59:59";
    }

    /**
     * @author liuao
     * @date 2018/4/4 10:40
     */
    public static String getCurrentTimeStr(String dateFormat) {
        SimpleDateFormat datef = new SimpleDateFormat(dateFormat);
        return datef.format(new Date());
    }

    /**
     * 日期加减一定天数
     *
     * @author liuao
     * @date 2018/4/11 19:52
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, n);
        return cal.getTime();
    }

    /**
     * 格式化日期
     *
     * @author liuao
     * @date 2018/4/11 19:52
     */
    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat datef = new SimpleDateFormat(dateFormat);
        return datef.format(date);
    }

    /**
     * @Description 获取当前时间
     * @author Joanne
     * @create 2018/6/6 15:36
     */
    public static String getDateTime() {

        return "" + new Date().getTime();
    }

    /**
     * 将秒转换成分：秒
     *
     * @param senond
     * @return
     */
    public static String getMmss(Integer senond) {
        String result = null;
        int minutes = senond / 60;
        int seconds = senond % 60;
        if (minutes < 10 && seconds < 10) {
            result = "0" + minutes + ":" + "0" + seconds;
        } else if (minutes < 10) {
            result = "0" + minutes + ":" + seconds;
        } else if (seconds < 10) {
            result = minutes + ":" + "0" + seconds;
        } else {
            result = minutes + ":" + seconds;
        }
        return result;
    }

    /**
     * 比较日期大小
     *
     * @param currentDate 当前时间
     * @param startDate   开始时间
     * @param endDate     结束个时间
     * @param dateFormat  日期格式
     * @return Integer null日期格式有误，"0-未开始；1-正在进行；2-已结束"
     */
    public static String compareDate(String currentDate, String startDate, String endDate, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date current = df.parse(currentDate);
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            if (current.getTime() < start.getTime()) {
                return SystemConstant.planStatus.notStart;
            } else if (start.getTime() <= current.getTime() && current.getTime() <= end.getTime()) {
                return SystemConstant.planStatus.running;
            } else if (current.getTime() > end.getTime()) {
                return SystemConstant.planStatus.over;
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
