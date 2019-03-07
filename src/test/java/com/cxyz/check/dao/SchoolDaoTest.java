package com.cxyz.check.dao;

import com.cxyz.check.entity.School;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SchoolDaoTest {

    @Autowired
    private SchoolDao schoolDao;

    @Test
    public void getCurrentTermId() {
    }

    @Test
    public void addSchool() {
        School school =new School("hello");
        schoolDao.addSchool(school);
        System.out.println(school.getId());
    }

    @Test
    public void updateSchool() {
        School school = new School();
        school.setId(1);
        school.setName(null);
        school.setManagerPwd("12345678");
        school.setTerm(new Term());
        schoolDao.updateSchool(school);
    }

    @Test
    public void deleteSchool() {
        schoolDao.deleteSchool(4);
    }
}