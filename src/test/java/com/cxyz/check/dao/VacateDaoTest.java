package com.cxyz.check.dao;

import com.cxyz.check.entity.Photo;
import com.cxyz.check.entity.User;
import com.cxyz.check.entity.Vacate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
        System.out.println(p);
    }

    @Test
    public void getGradeVacInDates() {
        dao.getGradeVacateInDates(1702,"2018-12-12 20:31","2018-12-12 20:33",true)
                .forEach(vacate -> {
                    System.out.println(vacate);
                });
    }

    @Test
    public void testAop()
    {
        Class[] cs = new Class[]{Usb.class};
        T t = new T();
        InvokeHandler handler = new InvokeHandler(t);
        ClassLoader loader = t.getClass().getClassLoader();
        Usb usb = (Usb) Proxy.newProxyInstance(loader, cs, handler);
        usb.connect("caonima");
    }
    interface Usb{

        void connect(String name);
    }

    class T implements Usb
    {

        @Override
        public void connect(String name) {
        }
    }

    class InvokeHandler implements InvocationHandler{

        Object target;

        public InvokeHandler(Object target)
        {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //执行织入的日志，你可以控制哪些方法执行切入逻辑
            if (method.getName().equals("connect")) {
                StringBuilder builder = new StringBuilder();
                builder.append("调用").append(target.getClass().getName()).append("的")
                        .append(method.getName()).append("方法,参数为:");
                int i = 0;
                for(Object arg:args)
                {
                    builder.append(arg);
                    if(i != args.length-1)
                        builder.append(',');
                    i++;
                }
                System.out.println(builder.toString());
            }
            //执行原有逻辑
            Object recv = method.invoke(target, args);
            return recv;
        }
    }
}