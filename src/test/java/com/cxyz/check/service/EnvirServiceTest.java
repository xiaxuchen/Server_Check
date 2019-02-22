package com.cxyz.check.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EnvirServiceTest {

    @Autowired
    private EnvirService service;

    @Test
    public void addTerm() {
    }

    @Test
    public void getCollegeGrades() {
    }

    @Test
    public void isUserImportEnable() {
        System.out.println(service.isUserImportEnable(1702));
    }

    @Test
    public void isLessonImportEnable() {

    }

    @Test
    public void toggleUserImport() {
    }

    @Test
    public void toggleLessonImport() {
    }

    @Test
    public void isCollegeUserImportEnable() {
    }

    @Test
    public void isCollegeLessonImportEnable() {
    }
}