package com.cxyz.check.dao;

import com.cxyz.check.entity.Push;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.typevalue.NotifyType;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class PushDaoTest {

    @Autowired
    private PushDao dao;

    @Test
    public void getPushes() throws GsonException {
    }

    @Test
    public void pushSuccess() {
        dao.pushSuccess(1);
    }

    @Test
    public void addPushes() {
        Push push = new Push();
        push.setReceiver(new User("17478093", UserType.STUDENT));
        push.setInfo("caonima");
        dao.addPushes(new ArrayList<Push>(){{add(push);}});
    }
}