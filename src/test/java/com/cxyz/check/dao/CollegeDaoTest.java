package com.cxyz.check.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@ContextConfiguration({"classpath:spring/spring-dao.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CollegeDaoTest {

    @Autowired
    CollegeDao collegeDao;
    @Test
    public void isUserImportEnable() {
        System.out.println(collegeDao.isUserImportEnable(1));
    }

    @Test
    public void isLessonImportEnable() {
        System.out.println(collegeDao.isLessonImportEnable(1));
    }

    @Test
    public void toggleUserImport() {
        collegeDao.toggleUserImport(1);
    }

    @Test
    public void toggleLessonImport() {
        collegeDao.toggleLessonImport(1);
    }
}