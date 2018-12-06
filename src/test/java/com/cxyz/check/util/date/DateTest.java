package com.cxyz.check.util.date;

import com.cxyz.check.util.parse.GsonUtil;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    private java.util.Date date1 = new java.util.Date();


    @Test
    public void toSDate() {
        System.out.println(Date.fromUDate(date1).toSDate());
        /**测试结果：成功
         * 2018-10-16
         */
    }

    @Test
    public void toUDate() {
        System.out.println(date1.toLocaleString());
        Date date = Date.fromUDate(date1);
        System.out.println(date);
        date.toUDate();
        /**测试结果：成功获取日期
         * 2018-10-16 16:44:53
         * 2018-10-16 0:00:00
         */
    }

}