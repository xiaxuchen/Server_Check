package com.cxyz.check.dao;

import com.cxyz.check.entity.Photo;
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

    @Test
    public void getVacates() {
        System.out.println(dao.getVacates("17478093",0,null));
    }

    @Test
    public void getVacatesToAudit() {
        System.out.println(dao.getVacatesToAudit("16478040",0));
    }

    @Test
    public void getDates()
    {
        System.out.println(dao.getDates(29));
    }

    @Test
    public void getPhotos()
    {
        System.out.println(dao.getPhotos(29));
    }

    @Test
    public void uploadPhoto()
    {
        Photo p = new Photo();
        p.setVac(new Vacate(31));
        p.setUri("cascafiiadsfg");
        System.out.println(p);
        dao.uploadPhoto(p);
        System.out.println(p);
    }
}