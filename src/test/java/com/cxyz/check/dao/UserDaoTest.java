package com.cxyz.check.dao;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.reflect.TypeToken;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
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
        logger.debug(dao.selectStusByGrade(1702));
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
        CheckResult<CheckTaskDto> o = null;
        try {
            o = (CheckResult<CheckTaskDto>) GsonUtil.fromJson("{\"success\":true,\"data\":{\"id\":5,\"name\":\"android\",\"sponsorName\":\"刘老师\",\"start\":\"1970-01-01 07:50:00\",\"end\":\"1970-01-01 23:59:59\",\"spot\":null},\"error\":null}", new TypeToken<CheckResult<CheckTaskDto>>() {
            }.getType());
        } catch (GsonException e) {
            e.printStackTrace();
        }
        System.out.println(o.getData());
    }
}