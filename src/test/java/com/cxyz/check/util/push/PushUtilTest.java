package com.cxyz.check.util.push;

import com.cxyz.check.entity.Times;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.shiro.realm.UserRealm;
import com.cxyz.check.shiro.token.UserToken;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.date.DateTime;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring-dao.xml",
                "classpath:spring/spring-service.xml", "classpath:spring/spring-shiro.xml"}
)
public class PushUtilTest {

    @Test
    public void jpushAndroid() {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("caonima","caonisma");
        PushUtil.jpushAndroid("111",objectObjectHashMap,"17478102");
    }

    @Autowired
    public UserRealm realm;


    @Test
    public void login()
    {
        /*SecurityManager manager = new DefaultSecurityManager();
        ((DefaultSecurityManager) manager).setRealm(realm);
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UserToken token = new UserToken("17478093","123456",UserType.STUDENT);
        subject.login(token);
        subject.checkRoles("checker");*/
        DateTime dateTime = new DateTime();
        dateTime.setHour(0).setMinute(0).setSecond(0).setYear(2018).setMonth(1).setDay(1);
        System.out.println(dateTime.toTimeStamp().toLocalDateTime());
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2018,1,1,0,0,0);
        System.out.println(new Timestamp(c.getTimeInMillis()).toLocalDateTime());
    }

    @Test
    public void test()
    {
        try {
            System.out.println(GsonUtil.fromJson("\"1970-01-01 08:00:00\"", new TypeToken<Timestamp>(){}.getType()));
        } catch (GsonException e) {
            e.printStackTrace();
        }
    }
}