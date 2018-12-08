package com.cxyz.check.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class EnvirDaoTest {

    @Autowired
    EnvirDao dao;
    @Test
    public void getUserNeedById() {
        System.out.println(dao.getUserNeedById(1702));
    }

    @Test
    public void getUserNeedById1() {
    }

    @Test
    public void checkTerm() {
        dao.checkTerm(1,"2018-1-1",1);
    }

    @Test
    public void addTerm() {
    }


    @Test
    public void getCurrentTerm() {
        System.out.println(dao.getCurrentTerm(1));
    }
}