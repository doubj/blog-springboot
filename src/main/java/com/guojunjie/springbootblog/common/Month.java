package com.guojunjie.springbootblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @time： 2019/11/29 16:27
 * @author： guojunjie
 * TODO
 */
@Getter
@AllArgsConstructor
public enum Month {
    /**
     * 月份的数字和对应的英文简写枚举类
     */
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

    /**
     * 根据数字月份获取对应的月份名
     * @param index 月份（数字）
     * @return 月份（英文简写）
     */
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

}
