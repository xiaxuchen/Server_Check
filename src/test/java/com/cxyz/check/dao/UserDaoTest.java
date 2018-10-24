package com.cxyz.check.dao;

import com.cxyz.check.typevalue.UserType;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    }

    @Test
    public void addStus() {
    }
}