package com.cxyz.check.service.impl;

import com.cxyz.check.service.RecordService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring-dao.xml",
                "classpath:spring/spring-service.xml"}
)
public class RecordServiceImplTest {

    @Autowired
    private RecordService service;
    @Test
    public void getCheckRecord() {
        System.out.println(service.getCheckRecord("17478093",0,1702));
    }
}