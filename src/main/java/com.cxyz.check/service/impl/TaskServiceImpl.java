package com.cxyz.check.service.impl;

import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.typevalue.TaskCompletionState;
import com.cxyz.check.util.automapper.AutoMapper;
import com.cxyz.check.util.date.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RecordDao recordDao;

    @Override
    public CheckTaskDto checkTask(String checker_id, int checker_type, int type) throws NoTaskException {
        DateTime dateTime = DateTime.fromUDate(new Date());
        //获取当前考勤
        TaskCompletion taskCompletion = taskDao.checkTask(checker_id, checker_type,
                dateTime.getDate(), type, dateTime.getTime());
        if(taskCompletion == null)
            throw new NoTaskException();
        User sponsor = taskCompletion.getTaskInfo().getSponsor();
        //获取发起者的名字
        sponsor.setName(userDao.getName(sponsor.getId()
                ,sponsor.getType()));
        CheckTaskDto checkTaskDto = new CheckTaskDto();
        AutoMapper.mapping(taskCompletion,checkTaskDto);
        return checkTaskDto;
    }

    @Transactional
    /**
     * TODO 需要增加事务
     * @param checkDto 考勤记录的dto
     */
    @Override
    public void commitCheck(CommitCheckDto checkDto) {
        if(checkDto == null)
            throw new CommitCheckFailException();
        int updated = 0;
        int state = checkDto.getState();
        if(state == TaskCompletionState.COMPLE)
        {
            if(checkDto.getStuInfos() != null)
            {
                updated = recordDao.addRecords(checkDto.getTaskId(),checkDto.getStuInfos());
                if(updated != checkDto.getStuInfos().size())
                    throw new CommitCheckFailException("服务器异常");
            }
        }else if(state == TaskCompletionState.OTHERSTATE)
        {
            if(null == checkDto.getDes())
                throw new CommitCheckFailException("非法情况:提交特殊情况时无描述信息");
            updated = recordDao.addOtherState(checkDto.getTaskId(),checkDto.getDes());
            if(updated == 0)
                throw new CommitCheckFailException();
        }else
        {
            throw new CommitCheckFailException();
        }
        updated = taskDao.updateComp(checkDto.getTaskId(),state);
        if(updated == 0)
            throw new CommitCheckFailException();
    }

    @Override
    public List<GradeStusDto> gradeStus(int grade) {
        List<User> users = userDao.selectStusByGrade(grade);
        if(users.size() == 0)
            throw new GradeNotFoundException();
        List<GradeStusDto> gradeStusDtos = AutoMapper.mappingList(users, GradeStusDto.class);
        return gradeStusDtos;
    }
}
