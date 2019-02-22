package com.cxyz.check.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring-dao.xml",
                "classpath:spring/spring-service.xml"}
)
public class RecordServiceTest {

    @Autowired
    private RecordService service;

    @Test
    public void getCheckRecords()
    {
        System.out.println(service.getRecordStatistic("17478093",0,1702));
    }

    @Test
    public void getCheckRecord() {
        System.out.println(service.getStatistic("2018-9-3","2018-10-1",1701));
    }
}