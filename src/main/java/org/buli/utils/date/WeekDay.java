package org.buli.utils.date;

public enum WeekDay {

    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6),
    Sunday(7);

    private final int day;

    WeekDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
