package com.cxyz.check.dao;

import com.cxyz.check.custom.ResultCustom;
import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.StatisticDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserDaoTest {

    @Autowired
    UserDao dao;
    Logger logger = Logger.getLogger(UserDaoTest.class);

    @Test
    public void selectStu() {
        logger.debug(dao.selectOne("17478093",UserType.STUDENT));
        logger.debug(dao.selectOne("17478093",UserType.TEACHER));
        /**
         * 测试成功
         */
    }

    @Test
    public void selectStusByGrade() {
        //logger.debug(dao.selectStusByGrade(1702));
        Date date = new Date();
        date.setDate(9);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        logger.debug(calendar.get(Calendar.DAY_OF_WEEK));
        /**
         * 测试成功
         */
    }


    @Test
    public void selectTeaByGrade() {
        logger.debug(dao.selectTeaByGrade(1));
        /**
         * 测试成功
         */
    }

    @Test
    public void selectTeaIDByGrade() {
        logger.debug(dao.selectTeaIDByGrade(1));
        /**
         * 测试成功
         */
    }

    @Test
    public void addStus() {
        User user = dao.selectOne("17478093", UserType.STUDENT);
        user.setId("17478096");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        User user1 = dao.selectOne("17478093", UserType.STUDENT);
        user1.setId("17478094");
        users.add(user1);
        dao.addUsers(users,UserType.STUDENT);
        /**
         * 测试成功
         */
    }

    @Test
    public void t()
    {
        /*StatisticDto dto = new StatisticDto();
        dto.setPersonCount(54);
        ResultCustom custom = new ResultCustom();
        custom.setResultType(CheckRecordResult.ABSENTEEISM);
        custom.setCount(1);
        custom.setResultType(CheckRecordResult.LATE);
        custom.setCount(3);
        dto.setResults(new ArrayList(){
            {add(custom);}
        });
        CheckResult<StatisticDto> checkResult = new CheckResult<>(dto);
        try {
            System.out.println(GsonUtil.toJson(checkResult));
        } catch (GsonException e) {
            e.printStackTrace();
        }*/

        String gson = "{\n" +
                "  \"success\": true,\n" +
                "  \"data\": {\n" +
                "    \"results\": [\n" +
                "      {\n" +
                "        \"resultType\": -2,\n" +
                "        \"count\": 3\n" +
                "      }\n" +
                "    ],\n" +
                "    \"personCount\": 54\n" +
                "  }\n" +
                "}";
        System.out.println(gson);

        try {
            CheckResult<StatisticDto> checkResult = (CheckResult<StatisticDto>) GsonUtil.fromJson(gson,new TypeToken<CheckResult<StatisticDto>>(){}.getType());
            System.out.println(checkResult.getData());
        } catch (GsonException e) {
            e.printStackTrace();
        }
    }
}