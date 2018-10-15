package com.cxyz.check.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class TeacherDaoTest {

    @Resource
    private TeacherDao teacherDao;
    @Test
    public void getTeaById() {
        System.out.print(teacherDao.getTeaById("17478093"));
    }
}