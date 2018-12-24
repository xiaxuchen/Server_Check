package com.cxyz.check.dao;

import com.cxyz.check.entity.User;
import com.cxyz.check.entity.Vacate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class VacateDaoTest {

    @Resource
    VacateDao dao;
    @Test
    public void addVacate() {
        Vacate v = new Vacate();
        v.setDes("caonima");
        v.setStart(new Timestamp(0));
        v.setEnd(new Timestamp(100000000));
        v.setSponsor(new User("17478093"));
        System.out.println(dao.addVacate(v));
    }
}