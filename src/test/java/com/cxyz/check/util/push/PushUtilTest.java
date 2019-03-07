package com.cxyz.check.util.push;

import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.VacateDto;
import com.cxyz.check.entity.Times;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.shiro.realm.UserRealm;
import com.cxyz.check.shiro.token.UserToken;
import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.typevalue.NotifyType;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.date.DateTime;
import com.cxyz.check.util.filepath.HashPathUtil;
import com.cxyz.check.util.parse.GsonUtil;
import com.cxyz.check.util.pinyin.PinYinUtil;
import com.google.gson.reflect.TypeToken;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
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
    public void te()
    {
        HashMap<String,String> map = new HashMap<>();
        map.put("type",NotifyType.VACATION_AUDIT+"");
        try {
            System.out.println(GsonUtil.toJson(map));
        } catch (GsonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jpushAndroid() {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", NotifyType.VACATION_AUDIT +"");
//        map.put("title","新消息");
//        map.put("content","caonima");
//        map.put("path","/main/LoginActivity");
//        map.put("ticker","ticker");
        PushUtil.jpushAndroid("111",map,"16478040");
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

    @Test
    public void temp()
    {
        File f = new File("F:\\program files\\apache-tomcat-8.5.34-windows-x64\\apache-tomcat-8.5.34\\webapps\\ROOT\\WEB-INF\\photo\\4\\7");
        System.out.println(f.exists());
        if(!f.exists())
            f.mkdirs();
        System.out.println(f.exists());
    }

    @Test
    public void hanYu()
    {
        String name = "萍乡学院";
        System.out.println(Integer.MAX_VALUE);
    }

}