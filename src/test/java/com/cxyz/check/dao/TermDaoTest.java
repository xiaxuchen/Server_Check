package com.cxyz.check.dao;

import com.cxyz.check.entity.Term;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class TermDaoTest {

    @Resource
    private TermDao dao;

    @Test
    public void getTermStart() {
        System.out.println(dao.getTermStart(1));
    }
}