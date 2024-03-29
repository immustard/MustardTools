package cn.buli_home.utils.date;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

import static cn.buli_home.utils.date.DateConstant.*;

public class DateUtils {

    /**
     * 时间格式化器集合
     */
    private static final Map<String, SimpleDateFormat> simpleDateFormatMap = new HashMap<String, SimpleDateFormat>();

    static {
        simpleDateFormatMap.put(FORMAT_DATE, new SimpleDateFormat(FORMAT_DATE));
        simpleDateFormatMap.put(FORMAT_HOUR, new SimpleDateFormat(FORMAT_HOUR));
        simpleDateFormatMap.put(FORMAT_MINUTE, new SimpleDateFormat(FORMAT_MINUTE));
        simpleDateFormatMap.put(FORMAT_SECOND, new SimpleDateFormat(FORMAT_SECOND));
        simpleDateFormatMap.put(FORMAT_MILLISECOND, new SimpleDateFormat(FORMAT_MILLISECOND));
        simpleDateFormatMap.put(FORMAT_TRIM_DATE, new SimpleDateFormat(FORMAT_TRIM_DATE));
        simpleDateFormatMap.put(FORMAT_TRIM_HOUR, new SimpleDateFormat(FORMAT_TRIM_HOUR));
        simpleDateFormatMap.put(FORMAT_TRIM_MINUTE, new SimpleDateFormat(FORMAT_TRIM_MINUTE));
        simpleDateFormatMap.put(FORMAT_TRIM_SECOND, new SimpleDateFormat(FORMAT_TRIM_SECOND));
        simpleDateFormatMap.put(FORMAT_TRIM_MILLISECOND, new SimpleDateFormat(FORMAT_TRIM_MILLISECOND));
    }

    /**
     * 获取指定时间格式化器
     *
     * @param formatStyle 时间格式
     * @return 时间格式化器
     */
    private static SimpleDateFormat p_getSimpleDateFormat(String formatStyle) {
        SimpleDateFormat dateFormat = simpleDateFormatMap.get(formatStyle);
        if (Objects.nonNull(dateFormat)) {
            return dateFormat;
        }
        return new SimpleDateFormat(formatStyle);
    }

    /**
     * 将 Date 格式时间转化为指定格式时间
     *
     * @param date        Date 格式时间
     * @param formatStyle 转化指定格式（如: yyyy-MM-dd HH:mm:ss）
     * @return 转化格式时间
     */
    public static String format(Date date, String formatStyle) {
        if (Objects.isNull(date)) {
            return "";
        }
        return p_getSimpleDateFormat(formatStyle).format(date);
    }

    /**
     * 将 Date 格式时间转化为 yyyy-MM-dd 格式时间
     *
     * @param date Date 格式时间
     * @return yyyy-MM-dd 格式时间（如：2022-06-17）
     */
    public static String formatDate(Date date) {
        return format(date, FORMAT_DATE);
    }

    /**
     * 将 Date 格式时间转化为 yyyy-MM-dd HH:mm:ss 格式时间
     *
     * @param date Date 格式时间
     * @return yyyy-MM-dd HH:mm:ss 格式时间（如：2022-06-17 16:06:17）
     */
    public static String formatDateTimeSecond(Date date) {
        return format(date, FORMAT_SECOND);
    }

    /**
     * 将 Date 格式时间转化为 yyyy-MM-dd HH:mm:ss:SSS 格式时间
     *
     * @param date Date 格式时间
     * @return yyyy-MM-dd HH:mm:ss:SSS 格式时间（如：2022-06-17 16:06:17:325）
     */
    public static String formatDateTimeMillisecond(Date date) {
        return format(date, FORMAT_MILLISECOND);
    }

    /**
     * 将 yyyy-MM-dd 格式时间转化为 Date 格式时间
     *
     * @param dateString yyyy-MM-dd 格式时间（如：2022-06-17）
     * @return Date 格式时间
     */
    public static Date parseDate(String dateString) {
        return parse(dateString, FORMAT_DATE);
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 格式时间转化为 Date 格式时间
     *
     * @param dateTimeStr yyyy-MM-dd HH:mm:ss 格式时间（如：2022-06-17 16:06:17）
     * @return Date 格式时间
     */
    public static Date parseDateTimeSecond(String dateTimeStr) {
        return parse(dateTimeStr, FORMAT_SECOND);
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss:SSS 格式时间转化为 Date 格式时间
     *
     * @param dateTimeStr yyyy-MM-dd HH:mm:ss:SSS 格式时间（如：2022-06-17 16:06:17）
     * @return Date 格式时间
     */
    public static Date parseDateTimeMillisecond(String dateTimeStr) {
        return parse(dateTimeStr, FORMAT_MILLISECOND);
    }

    /**
     * 将字符串格式时间转化为 Date 格式时间
     *
     * @param dateString 字符串时间（如：2022-06-17 16:06:17）
     * @return formatStyle 格式内容
     * @return Date 格式时间
     */
    public static Date parse(String dateString, String formatStyle) {
        String s = p_getString(dateString);
        if (s.isEmpty()) {
            return null;
        }
        try {
            return p_getSimpleDateFormat(formatStyle).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取字符串有效内容
     *
     * @param s 字符串
     * @return 有效内容
     */
    private static String p_getString(String s) {
        return Objects.isNull(s) ? "" : s.trim();
    }

    /**
     * 获取一天的开始时间（即：0 点 0 分 0 秒 0 毫秒）
     *
     * @param date 指定时间
     * @return 当天的开始时间
     */
    public static Date getDateStart(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的截止时间（即：23 点 59 分 59 秒 999 毫秒）
     *
     * @param date 指定时间
     * @return 当天的开始时间
     */
    public static Date getDateEnd(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取日期数字
     *
     * @param date 日期
     * @return 日期数字
     */
    public static int getDateNo(Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        return Integer.parseInt(format(date, FORMAT_TRIM_DATE));
    }

    /**
     * 获取日期时间数字（到秒）
     *
     * @param date 日期
     * @return 日期数字
     */
    public static long getDateTimeSecond(Date date) {
        if (Objects.isNull(date)) {
            return 0L;
        }
        return Long.parseLong(format(date, FORMAT_TRIM_SECOND));
    }

    /**
     * 获取日期时间数字（到毫秒）
     *
     * @param date 日期
     * @return 日期数字
     */
    public static long getDateTimeMillisecond(Date date) {
        if (Objects.isNull(date)) {
            return 0L;
        }
        return Long.parseLong(format(date, FORMAT_TRIM_MILLISECOND));
    }

    /**
     * 获取时间戳
     *
     * @param date 日期
     * @return 时间戳(秒级)
     */
    public static long getTimestampSecond(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * 获取时间戳
     *
     * @param date 日期
     * @return 时间戳(毫秒级)
     */
    public static long getTimestampMillisecond(Date date) {
        return date.getTime();
    }

    /**
     * 获取星期几
     *
     * @param date 时间
     * @return 0（时间为空）， 1（周一）， 2（周二），3（周三），4（周四），5（周五），6（周六），7（周日）
     */
    private static int p_getWeek(Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return p_getWeek(calendar);
    }

    /**
     * 获取星期几
     *
     * @return 0（时间为空）， 1（周一）， 2（周二），3（周三），4（周四），5（周五），6（周六），7（周日）
     */
    private static int p_getWeek(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    /**
     * 获取该日期是今年的第几周（以本年的周一为第1周，详见下面说明）<br>
     * <p>
     * 【说明】<br>
     * 比如 2022-01-01（周六）和 2022-01-02（周日）虽然在 2022 年里，但他们两天则属于 2021 年最后一周，<br>
     * 那么这两天不会算在 2022 年第 1 周里，此时会返回 0 ；而 2022 年第 1 周将从 2022-01-03（周一） 开始计算。<br>
     *
     * @param date 时间
     * @return -1（时间为空）， 0（为上个年的最后一周），其他数字（今年的第几周）
     */
    public static int getWeekOfYear(Date date) {
        if (Objects.isNull(date)) {
            return -1;
        }
        int weeks = getWeekOfYearIgnoreLastYear(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int week = p_getWeek(calendar);
        if (week == 1) {
            return weeks;
        }
        return weeks - 1;
    }

    /**
     * 获取今年的第几周（以本年的1月1日为第1周第1天）<br>
     *
     * @param date 时间
     * @return -1（时间为空），其他数字（今年的第几周）
     */
    public static int getWeekOfYearIgnoreLastYear(Date date) {
        if (Objects.isNull(date)) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int days = calendar.get(Calendar.DAY_OF_YEAR);
        int weeks = days / 7;
        // 如果是 7 的倍数，则表示恰好是多少周
        if (days % 7 == 0) {
            return weeks;
        }
        // 如果有余数，则需要再加 1
        return weeks + 1;
    }

    /**
     * 获取时间节点对象
     *
     * @param date 时间对象
     * @return DateNode
     */
    public static DateNode getDateNode(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateNode node = new DateNode();
        node.setTime(format(date, FORMAT_MILLISECOND));
        node.setYear(calendar.get(Calendar.YEAR));
        node.setMonth(calendar.get(Calendar.MONTH) + 1);
        node.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        node.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        node.setMinute(calendar.get(Calendar.MINUTE));
        node.setSecond(calendar.get(Calendar.SECOND));
        node.setMillisecond(calendar.get(Calendar.MILLISECOND));
        node.setWeek(p_getWeek(calendar));
        node.setDayOfYear(calendar.get(Calendar.DAY_OF_YEAR));
        node.setWeekOfYear(getWeekOfYear(date));
        node.setWeekOfYearIgnoreLastYear(getWeekOfYearIgnoreLastYear(date));
        node.setMillisecondStamp(date.getTime());
        node.setSecondStamp(date.getTime() / 1000);
        return node;
    }

    /**
     * 日期变更
     *
     * @param date   指定日期
     * @param field  变更属性（如变更年份，则该值为 Calendar.DAY_OF_YEAR）
     * @param amount 变更大小（大于 0 时增加，小于 0 时减少）
     * @return 变更后的日期时间
     */
    public static Date add(Date date, int field, int amount) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 指定日期加减年份
     *
     * @param date 指定日期
     * @param year 变更年份（大于 0 时增加，小于 0 时减少）
     * @return 变更年份后的日期
     */
    public static Date addYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 指定日期加减月份
     *
     * @param date  指定日期
     * @param month 变更月份（大于 0 时增加，小于 0 时减少）
     * @return 变更月份后的日期
     */
    public static Date addMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 指定日期加减天数
     *
     * @param date 指定日期
     * @param day  变更天数（大于 0 时增加，小于 0 时减少）
     * @return 变更天数后的日期
     */
    public static Date addDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 指定日期加减星期
     *
     * @param date 指定日期
     * @param week 变更星期数（大于 0 时增加，小于 0 时减少）
     * @return 变更星期数后的日期
     */
    public static Date addWeek(Date date, int week) {
        return add(date, Calendar.WEEK_OF_YEAR, week);
    }

    /**
     * 指定日期加减小时
     *
     * @param date 指定日期时间
     * @param hour 变更小时数（大于 0 时增加，小于 0 时减少）
     * @return 变更小时数后的日期时间
     */
    public static Date addHour(Date date, int hour) {
        return add(date, Calendar.HOUR_OF_DAY, hour);
    }

    /**
     * 指定日期加减分钟
     *
     * @param date   指定日期时间
     * @param minute 变更分钟数（大于 0 时增加，小于 0 时减少）
     * @return 变更分钟数后的日期时间
     */
    public static Date addMinute(Date date, int minute) {
        return add(date, Calendar.MINUTE, minute);
    }

    /**
     * 指定日期加减秒
     *
     * @param date   指定日期时间
     * @param second 变更秒数（大于 0 时增加，小于 0 时减少）
     * @return 变更秒数后的日期时间
     */
    public static Date addSecond(Date date, int second) {
        return add(date, Calendar.SECOND, second);
    }

    /**
     * 指定日期加减秒
     *
     * @param date        指定日期时间
     * @param millisecond 变更毫秒数（大于 0 时增加，小于 0 时减少）
     * @return 变更毫秒数后的日期时间
     */
    public static Date addMillisecond(Date date, int millisecond) {
        return add(date, Calendar.MILLISECOND, millisecond);
    }

    /**
     * 获取该日期所在周指定星期的日期
     *
     * @param date 日期所在时间
     * @return index 指定星期（1 - 7 分别对应星期一到星期天）
     */
    public static Date getWeekDate(Date date, WeekDay weekDay) {
        int week = p_getWeek(date);
        return addDay(date, weekDay.getDay() - week);
    }

    /**
     * 获取该日期所在周开始日期
     *
     * @param date 日期所在时间
     * @return 所在周开始日期
     */
    public static Date getWeekDateStart(Date date) {
        return getDateStart(getWeekDate(date, WeekDay.Monday));
    }

    /**
     * 获取该日期所在周开始日期
     *
     * @param date 日期所在时间
     * @return 所在周开始日期
     */
    public static Date getWeekDateEnd(Date date) {
        return getWeekDateEnd(getWeekDate(date, WeekDay.Sunday));
    }

    /**
     * 获取该日期所在周的所有日期（周一到周日）
     *
     * @param date 日期
     * @return 该日照所在周的所有日期
     */
    public static List<Date> getWeekDateList(Date date) {
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        // 获取本周开始时间
        Date weekFromDate = getWeekDateStart(date);
        // 获取本周截止时间
        Date weekeEndDate = getWeekDateEnd(date);
        return getBetweenDateList(weekFromDate, weekeEndDate, true);
    }

    /**
     * 获取该日期所在周的所有日期（周一到周日）
     *
     * @param dateString
     * @return 该日照所在周的所有日期
     */
    public static List<String> getWeekDateList(String dateString) {
        Date date = parseDate(dateString);
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        return getDateStrList(getWeekDateList(date));
    }

    /**
     * 获取该日期所在月的所有日期
     *
     * @param date
     * @return 该日照所月的所有日期
     */
    public static List<Date> getMonthDateList(Date date) {
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        Date monthDateStart = getMonthDateStart(date);
        Date monthDateEnd = getMonthDateEnd(date);
        return getBetweenDateList(monthDateStart, monthDateEnd, true);
    }

    /**
     * 获取该日期所在月的所有日期
     *
     * @param dateString
     * @return 该日照所月的所有日期
     */
    public static List<String> getMonthDateList(String dateString) {
        Date date = parseDate(dateString);
        if (Objects.isNull(date)) {
            return Collections.emptyList();
        }
        return getDateStrList(getMonthDateList(date));
    }

    /**
     * 获取本日期所在月第一天
     *
     * @param date 日期
     * @return 本日期所在月第一天
     */
    public static Date getMonthDateStart(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDateStart(calendar.getTime());
    }

    /**
     * 获取本日期所在月最后一天
     *
     * @param date 日期
     * @return 本日期所在月最后一天
     */
    public static Date getMonthDateEnd(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        Date monthDateStart = getMonthDateStart(date);
        Date nextMonthDateStart = getMonthDateStart(addMonth(monthDateStart, 1));
        return getDateEnd(addDay(nextMonthDateStart, -1));
    }

    /**
     * 比较两个日期的大小 (毫秒级)
     * @param date1 日期1
     * @param date2 日期2
     * @return 返回值: 1, 前者大于后者; 0, 两者相同; -1, 前者小于后者; null, 日期中有空
     */
    public static Integer compareDays(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return null;
        }

        long ts1 = getTimestampMillisecond(date1);
        long ts2 = getTimestampMillisecond(date2);

        if (ts2 - ts1 == 0) {
            return 0;
        } else if (ts1 > ts2) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 获取两个日期相差的天数（以日期为单位计算，不以24小时制计算，详见下面说明）<br>
     * <p>
     * 【说明】比如 2022-06-17 23:00:00 和 2022-06-17 01:00:00，两者虽然只相差 2 个小时，但也算相差 1 天 <br>
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差天数（若返回 -1，则至少有一个日期存在为空，此时不能进行比较）
     */
    public static int countBetweenDays(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return -1;
        }
        // 获取两个日期 0 点 0 时 0 分 0 秒 0 毫秒时的时间戳（毫秒级）
        long t1 = getDateStart(date1).getTime();
        long t2 = getDateStart(date2).getTime();
        // 相差天数 = 相差的毫秒数 / 一天的毫秒数
        return (int) (Math.abs(t1 - t2) / DateConstant.DAY_MILLISECONDS);
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 两个日期之间的所有日期的开始时间
     */
    public static List<Date> getBetweenDateList(Date date1, Date date2) {
        return getBetweenDateList(date1, date2, false);
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 两个日期之间的所有日期的开始时间
     */
    public static List<Date> getBetweenDateList(Date date1, Date date2, boolean isContainParams) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return Collections.emptyList();
        }
        // 确定前后日期
        Date fromDate = date1;
        Date toDate = date2;
        if (date2.before(date1)) {
            fromDate = date2;
            toDate = date1;
        }
        // 获取两个日期每天的开始时间
        Date from = getDateStart(fromDate);
        Date to = getDateStart(toDate);
        // 获取日期，开始循环
        List<Date> dates = new ArrayList<Date>();
        if (isContainParams) {
            dates.add(from);
        }
        Date date = from;
        boolean isBefore = true;
        while (isBefore) {
            date = addDay(date, 1);
            isBefore = date.before(to);
            if (isBefore) {
                dates.add(getDateStart(date));
            }
        }
        if (isContainParams) {
            dates.add(to);
        }
        return dates;
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param dateString1 日期1（如：2022-06-20）
     * @param dateString2 日期2（如：2022-07-15）
     * @return 两个日期之间的所有日期（不包含参数日期）
     */
    public static List<String> getBetweenDateList(String dateString1, String dateString2) {
        return getBetweenDateList(dateString1, dateString2, false);
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param dateString1     日期1（如：2022-06-20）
     * @param dateString2     日期2（如：2022-07-15）
     * @param isContainParams 是否包含参数的两个日期
     * @return 两个日期之间的所有日期的开始时间
     */
    public static List<String> getBetweenDateList(String dateString1, String dateString2, boolean isContainParams) {
        Date date1 = parseDate(dateString1);
        Date date2 = parseDate(dateString2);
        List<Date> dates = getBetweenDateList(date1, date2, isContainParams);
        return getDateStrList(dates);
    }

    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日，标准日期字符串
     * @return 年龄
     */
    public static int ageOfNow(String birthDay, String formatStyle) {
        return ageOfNow(parse(birthDay, formatStyle));
    }

    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日
     * @return 年龄
     */
    public static int ageOfNow(Date birthDay) {
        return age(birthDay, new Date());
    }

    /**
     * 计算相对于dateToCompare的年龄，常用于计算指定生日在某年的年龄
     *
     * @param birthday      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    public static int age(Date birthday, Date dateToCompare) {
        if (Objects.isNull(birthday)) {
            return -1;
        }

        if (null == dateToCompare) {
            dateToCompare = new Date();
        }

        return age(birthday.getTime(), dateToCompare.getTime());
    }

    /**
     * 计算相对于dateToCompare的年龄，常用于计算指定生日在某年的年龄<br>
     * 按照《最高人民法院关于审理未成年人刑事案件具体应用法律若干问题的解释》第二条规定刑法第十七条规定的“周岁”，按照公历的年、月、日计算，从周岁生日的第二天起算。
     * <ul>
     *     <li>2022-03-01出生，则相对2023-03-01，周岁为0，相对于2023-03-02才是1岁。</li>
     *     <li>1999-02-28出生，则相对2000-02-29，周岁为1</li>
     * </ul>
     *
     * @param birthday      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     */
    protected static int age(long birthday, long dateToCompare) {
        if (birthday > dateToCompare) {
            throw new IllegalArgumentException("Birthday is after dateToCompare!");
        }

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateToCompare);

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        // 复用cal
        cal.setTimeInMillis(birthday);
        int age = year - cal.get(Calendar.YEAR);

        //当前日期，则为0岁
        if (age == 0){
            return 0;
        }

        final int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {
            final int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            // issue#I6E6ZG，法定生日当天不算年龄，从第二天开始计算
            if (dayOfMonth <= dayOfMonthBirth) {
                // 如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            // 如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }

    /**
     * List<Date> 转 List<String>
     *
     * @param dates 日期集合
     * @return 日期字符串集合
     */
    public static List<String> getDateStrList(List<Date> dates) {
        if (dates.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> dateList = new ArrayList<String>();
        for (Date date : dates) {
            dateList.add(formatDate(date));
        }
        return dateList;
    }

    public static String convertMillisecond2TimeStr(long millisecond) {
        if (millisecond < SECOND_MILLISECONDS) {
            return millisecond + "ms";
        }

        long sec = millisecond / SECOND_MILLISECONDS;
        long mil = millisecond % SECOND_MILLISECONDS;

        return convertSecond2TimeStr(sec) + mil + "ms";
    }

    public static String convertSecond2TimeStr(long second) {
        if (second < MINUTE_SECONDS) {
            return second + "s";
        }

        long min = second / MINUTE_SECONDS;
        long sec = second % MINUTE_SECONDS;

        return convertMinute2TimeStr(min) + sec + "s";
    }

    public static String convertMinute2TimeStr(long minute) {
        if (minute < HOUR_MINUTES) {
            return minute + "m";
        } else if (minute < DAY_MINUTES) {
            long hour = minute / HOUR_MINUTES;
            long min = minute % HOUR_MINUTES;

            return hour + "h" + min + "m";
        }

        long day = minute / DAY_MINUTES;
        long hour = (minute % DAY_MINUTES) / HOUR_MINUTES;
        long min = minute % HOUR_MINUTES;

        return day + "d" + hour + "h" + min + "m";
    }

    @Data
    public static class DateNode {
        /**
         * 年
         */
        private int year;
        /**
         * 月
         */
        private int month;
        /**
         * 日
         */
        private int day;
        /**
         * 时
         */
        private int hour;
        /**
         * 分
         */
        private int minute;
        /**
         * 秒
         */
        private int second;
        /**
         * 毫秒
         */
        private int millisecond;
        /**
         * 星期几（ 1 - 7 对应周一到周日）
         */
        private int week;
        /**
         * 当年第几天
         */
        private int dayOfYear;
        /**
         * 当年第几周（本年周 1 为第 1 周，0 则表示属于去年最后一周）
         */
        private int weekOfYear;
        /**
         * 当年第几周（本年周 1 为第 1 周，0 则表示属于去年最后一周）
         */
        private int weekOfYearIgnoreLastYear;
        /**
         * 时间戳（秒级）
         */
        private long secondStamp;
        /**
         * 时间戳（毫秒级）
         */
        private long millisecondStamp;
        /**
         * 显示时间
         */
        private String time;
    }
}