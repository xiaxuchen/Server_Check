package com.cxyz.check.service.impl;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring_dao.xml",
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
        CheckResult<LoginDto> result  = gson.fromJson("{\n" +
                "  \"success\": true,\n" +
                "  \"data\": {\n" +
                "    \"Caonima\": \"夏旭晨\",\n" +
                "    \"sex\": \"男\",\n" +
                "    \"phone\": \"17779911413\",\n" +
                "    \"power\": 5,\n" +
                "    \"collegeName\": \"信息与计算机工程学院\",\n" +
                "    \"gradeId\": 1702,\n" +
                "    \"gradeName\": \"17软工二班\"\n" +
                "  }\n" +
                "}", new TypeToken<CheckResult<LoginDto>>() {
        }.getType());
        logger.debug(result.getData());
    }
}