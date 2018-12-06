package com.cxyz.check.service.impl;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring-dao.xml",
                "classpath:spring/spring-service.xml"}
)
public class UserServiceImplTest {

    @Autowired
    private UserService service;
    Logger logger = Logger.getLogger(UserServiceImplTest.class);
    @Test
    public void login() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()//格式化输出（序列化）
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //序列化日期格式化输出
                .create();
        String s = gson.toJson(new CheckResult(service.login("17478093", "123456", 0)));
        logger.debug(gson.toJson(new Timestamp(new Date().getTime())));
    }

    /**
     * 测试成功
     */
    @Test
    public void gradeStus()
    {
        service.getGradeStus(1702);
    }
}