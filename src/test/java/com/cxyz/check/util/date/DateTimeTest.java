package com.cxyz.check.util.date;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTimeTest {

    @Test
    public void fromTS()
    {
        System.out.println(DateTime.fromTS(DateTime.
                fromUDate(new java.util.Date()).toTimeStamp()).toString());
        /**
         * 测试结果：成功
         * 2018:10:16:17:32:53
         */
    }

    @Test
    public void toTimeStamp() {
        System.out.println(new java.util.Date().getTime());
        System.out.println(DateTime.fromUDate(new java.util.Date()).toTimeStamp().getTime());
        /**
         * 测试结果：成功
         * 1539682077600
         * 1539682077000
         */
    }
}