package com.cxyz.check.dao;

import com.cxyz.check.typevalue.UserType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class TaskDaoTest {

    @Resource
    private TaskDao dao;
    @Test
    public void checkTask() {
        System.out.println(dao.checkTask("17478093",
                UserType.STUDENT,"2018-11-10",1,"08:00:00"));
    }

    @Test
    public void updateComp() {
        /**
         * 测试成功
         */
        System.out.println(dao.updateComp(1,0));
    }

    @Test
    public void getCompTime()
    {
        System.out.println(dao.getCompDate(1));
    }

    @Test
    public void addComp()
    {
        int year = 2018;
        int month = 11;
        int day = 12;
        while(true) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);
            int weekofday = calendar.get(Calendar.DAY_OF_WEEK)-1==0?7:calendar.get(Calendar.DAY_OF_WEEK)-1;
            Date date = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.CHINA);
            if(calendar.get(Calendar.DAY_OF_MONTH) == 30)
                break;
            dao.addComp(dateFormat.format(date),15);
            day++;
        }

    }


}