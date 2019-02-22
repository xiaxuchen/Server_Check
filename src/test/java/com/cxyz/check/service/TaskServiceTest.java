package com.cxyz.check.service;

import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.entity.Lesson;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.typevalue.TaskCompletionState;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.date.DateTime;
import com.cxyz.check.util.parse.GsonUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring-dao.xml",
                "classpath:spring/spring-service.xml"}
)
public class TaskServiceTest {

    @Autowired
    private TaskService service;

    @Autowired
    private LessonService lessonService;

    @Test
    public void checkTask() {
//            System.out.println(service.getStatisticExcel("17478093",UserType.TEACHER,443));
       /*DateTime time = new DateTime();
       time.setYear(2018);
       time.setMonth(12);
       time.setDay(12);
       time.setHour(9);
       System.out.println(time.getTime());*/
    }

    @Test
    public void commitCheck() {
        CommitCheckDto taskDto = new CommitCheckDto();
        taskDto.setTaskId(1);
        taskDto.setState(TaskCompletionState.COMPLE);
        taskDto.setDes("狗东西打了老师，老师罢课");
        service.commitCheck(taskDto);
    }

    @Test
    public void getSubjects() {
        service.getSubjects(1702);
    }

    @Test
    public void getStatisticExcel() throws IOException {
        service.getStatisticExcel("17478093",1,443).write(new FileOutputStream("d:/test.xlsx"));
    }
}