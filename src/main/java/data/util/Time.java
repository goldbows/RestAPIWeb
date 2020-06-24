package data.util;

public enum Time {
    AB(1),
    AC(2),
    AD(3),
    BC(1),
    BD(2),
    CD(1),
    BA(1),
    CA(2),
    DA(3),
    CB(1),
    DB(2),
    DC(1);

    Integer time;

    public Integer getTime() {
        return this.time;
    }

    Time(Integer time) {
        this.time = time;
    }
}
