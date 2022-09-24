package cn.buli_home.utils.date;

public interface DateConstant {

    /** 一星期的天数 */
    int WEEK_DAYS = 7;
    /** 一年的月份数 */
    int YEAR_MONTHS = 12;
    /** 一天的小时数 */
    int DAY_HOURS = 24;
    /** 一小时分钟数 */
    int HOUR_MINUTES = 60;
    /** 一天分钟数 (24 * 60) */
    int DAY_MINUTES = 1440;
    /** 一分钟的秒数 */
    int MINUTE_SECONDS = 60;
    /** 一个小时的秒数 (60 * 60) */
    int HOUR_SECONDS = 3600;
    /** 一天的秒数 (24 * 60 * 60) */
    int DAY_SECONDS = 86400;
    /** 一秒的毫秒数 */
    long SECOND_MILLISECONDS = 1000L;
    /** 一分钟的毫秒数（60 * 1000） */
    long MINUTE_MILLISECONDS = 60000L;
    /** 一小时的毫秒数（60 * 60 * 1000） */
    long HOUR_MILLISECONDS = 3600000L;
    /** 一天的毫秒数（24 * 60* 60* 1000） */
    long DAY_MILLISECONDS = 86400000L;

    /** 显示到日期 */
    String FORMAT_DATE = "yyyy-MM-dd";
    /** 显示到小时 */
    String FORMAT_HOUR = "yyyy-MM-dd HH";
    /** 显示到分 */
    String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
    /** 显示到秒 */
    String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    /** 显示到毫秒 */
    String FORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss:SSS";
    /** 显示到日期（数字格式） */
    String FORMAT_TRIM_DATE = "yyyyMMdd";
    /** 显示到小时（数字格式） */
    String FORMAT_TRIM_HOUR = "yyyyMMddHH";
    /** 显示到分（数字格式） */
    String FORMAT_TRIM_MINUTE = "yyyyMMddHHmm";
    /** 显示到秒（数字格式） */
    String FORMAT_TRIM_SECOND = "yyyyMMddHHmmss";
    /** 显示到毫秒（数字格式） */
    String FORMAT_TRIM_MILLISECOND = "yyyyMMddHHmmssSSS";
    
}
