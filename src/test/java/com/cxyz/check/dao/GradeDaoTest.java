package com.cxyz.check.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class GradeDaoTest {

    @Autowired
    private GradeDao dao;

    @Test
    public void getGradeSchoolId() {
        System.out.println(dao.getGradeSchoolId(1702));
    }

    @Test
    public void getCollegeGrades() {
        System.out.println(dao.getCollegeGrades(1));
    }
}