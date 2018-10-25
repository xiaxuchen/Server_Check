package com.cxyz.check.util.automapper;

import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.LoginDto;
import com.cxyz.check.entity.User;
import com.cxyz.check.typevalue.UserType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring_dao.xml",
                "classpath:spring/spring-service.xml"})
public class AutoMapperTest {

    @Autowired
    UserDao dao;
    @Test
    public void mapping() {
        User user = dao.selectOne("17478093", UserType.STUDENT);
        System.out.println(AutoMapper.mapping(user,new LoginDto()));
    }

    @Test
    public void mappingList() {
    }
}