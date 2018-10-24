package com.cxyz.check.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class StudentDaoTest {

    @Resource
    private StudentDao studentDao;
    @Test
    public void getStuById() {
        System.out.print(studentDao.getStuById("17478093"));
        //测试结果：
        /*
        Student{OldUser{id='17478093', name='夏旭晨', sex='男',
         pwd='123456', phone='17779911413', photo='111', power=5,
         type=0}grade=Grade [name=null, college=null,
        headTeacher=null, classRoom=null, id=122],
        collegeName='信计学院'}
         */
    }

    @Test
    public void getStusByGrade() {
        System.out.print(studentDao.getStusByGrade(122));
        //测试结果:
        /*
        [Student{OldUser{id='17478063', name='陈宸',
         sex='男', pwd='123456', phone='null', photo='null'
         , power=0, type=0}grade=Grade [name=null, college=null
         , headTeacher=null, classRoom=null, id=122], collegeName='null'
         }, Student{OldUser{id='17478090', name='张奕文', sex='null', pwd='123456'
         , phone='null', photo='null', power=0, type=0}grade=Grade [name=null,
          college=null, headTeacher=null, classRoom=null, id=122],
          collegeName='null'}, Student{OldUser{id='17478091', name='喻济生',
           sex='男', pwd='123456', phone='null', photo='null', power=0,
            type=0}grade=Grade [name=null, college=null, headTeacher=null,
             classRoom=null, id=122], collegeName='null'},
             Student{OldUser{id='17478093', name='夏旭晨', sex='男',
              pwd='123456', phone='17779911413', photo='111', power=5,
               type=0}grade=Grade [name=null, college=null, headTeacher=null
               , classRoom=null, id=122], collegeName='信计学院'}
         */
    }
}