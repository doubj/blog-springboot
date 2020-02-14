package com.guojunjie.springbootblog.common;

/**
 * @time： 2019/11/29 16:27
 * @author： guojunjie
 * TODO
 */
public enum Month {
    January("Jan", 1),
    February("Feb", 2),
    March("Mar", 3),
    April("Apr", 4),
    May("May", 5),
    June("Jun", 6),
    July("Jul", 7),
    August("Aug", 8),
    September("Sept", 9),
    October("Oct", 10),
    November("Nov", 11),
    December("Dec", 12);

    public static String getName(int index) {
        for (Month month : Month.values()) {
            if (month.getIndex() == index) {
                return month.name;
            }
        }
        return null;
    }

    private String name;
    private int index;

    private Month(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
