package com.cxyz.check.service;

import com.cxyz.check.typevalue.UserType;

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
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Test
    public void login() {
        System.out.print(userService.login("17478093",
                "123456", UserType.STUDENT));
        /**测试结果：成功
         * LoginDto{id='17478093', name='夏旭晨', sex='男'
         * , phone='17779911413', photo='111', power=5,
         * type=0, error='null', CollegeName='信计学院',
         * collegeId='null', gradeId=122, gradeName='null'}
         */
    }

    @Test
    public void getGradeStus() {
       //System.out.print(userService.getGradeStus(122));
        /**测试结果：成功
         * [LoginDto{id='17478063', name='陈宸',
         * sex='男', phone='null', photo='null',
         * power=0, type=0, error='null', CollegeName='null',
         * collegeId='null', gradeId=122, gradeName='null'},
         * LoginDto{id='17478090', name='张奕文', sex='null',
         * phone='null', photo='null', power=0, type=0, error='null',
         * CollegeName='null', collegeId='null', gradeId=122, gradeName='null'},
         * LoginDto{id='17478091', name='喻济生', sex='男', phone='null',
         * photo='null', power=0, type=0, error='null', CollegeName='null',
         * collegeId='null', gradeId=122, gradeName='null'},
         * LoginDto{id='17478093', name='夏旭晨', sex='男',
         * phone='17779911413', photo='111', power=5, type=0,
         * error='null', CollegeName='信计学院', collegeId='null',
         * gradeId=122, gradeName='null'}]
         */
    }

}