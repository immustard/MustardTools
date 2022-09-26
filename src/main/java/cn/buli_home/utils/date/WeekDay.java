package cn.buli_home.utils.date;

public enum WeekDay {

    /** 星期一 */
    Monday(1),
    /** 星期二 */
    Tuesday(2),
    /** 星期三 */
    Wednesday(3),
    /** 星期四 */
    Thursday(4),
    /** 星期五 */
    Friday(5),
    /** 星期六 */
    Saturday(6),
    /** 星期日 */
    Sunday(7);

    private final int day;

    WeekDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
