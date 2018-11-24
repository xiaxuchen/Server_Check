package com.cxyz.check.service;

import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.typevalue.TaskCompletionState;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.parse.GsonUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        {"classpath:spring/spring_dao.xml",
                "classpath:spring/spring-service.xml"}
)
public class TaskServiceTest {

    @Autowired
    private TaskService service;
    @Test
    public void checkTask() {
        try {
            System.out.println(GsonUtil.toJson(service.checkTask("17478093", UserType.STUDENT,1)));
        } catch (GsonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void commitCheck() {
        CommitCheckDto taskDto = new CommitCheckDto();
        taskDto.setTaskId(1);
        taskDto.setState(TaskCompletionState.COMPLE);
        taskDto.setDes("狗东西打了老师，老师罢课");
        service.commitCheck(taskDto);
    }
}