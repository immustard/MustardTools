package cn.buli_home.utils.date;

public enum Month {

    /** 一月 */
    January(1),
    /** 二月 */
    February(2),
    /** 三月 */
    March(3),
    /** 四月 */
    April(4),
    /** 五月 */
    May(5),
    /** 六月 */
    June(6),
    /** 七月 */
    July(7),
    /** 八月 */
    August(8),
    /** 九月 */
    September(9),
    /** 十月 */
    October(10),
    /** 十一月 */
    November(11),
    /** 十二月 */
    December(12);

    private final int month;

    Month(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }
}
