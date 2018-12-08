package com.cxyz.check.service.impl;

import com.cxyz.check.dao.GradeDao;
import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dao.SchoolDao;
import com.cxyz.check.dao.TaskCompletionDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dao.TermDao;
import com.cxyz.check.dao.TimesDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Times;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.typevalue.PowerType;
import com.cxyz.check.typevalue.TaskCompletionState;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.automapper.AutoMapper;
import com.cxyz.check.util.date.DateTime;

import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskCompletionDao taskCompletionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskCompletionDao completionDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private TermDao termDao;

    @Autowired
    private TimesDao timesDao;

    @Override
    public CheckTaskDto checkTask(String checker_id, int checker_type, int type) throws NoTaskException {
        //TODO 这里用作测试
        DateTime dateTime = new DateTime();
        dateTime.setYear(2018);
        dateTime.setMonth(9);
        dateTime.setDay(3);
        dateTime.setHour(9);
        System.out.println(dateTime.getTime());
        //获取当前考勤
        TaskCompletion taskCompletion = taskDao.checkTask(checker_id, checker_type,
                dateTime.getDate(), type, dateTime.getTime());
        System.out.println(taskCompletion);
        if(taskCompletion == null)
            throw new NoTaskException();
        User sponsor = taskCompletion.getTaskInfo().getSponsor();
        if(sponsor != null)
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
        updated = completionDao.updateCompState(state,checkDto.getTaskId());
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

    @Override
    @Transactional
    public void addTask(List<TaskInfo> taskInfos,Integer type,Integer gradeId) {
        int termId = schoolDao.getCurrentTermId(gradeDao.getGradeSchoolId(gradeId));
        if(termId == 0)
            throw new TransactionalException("未知错误");
        //为任务设置考勤人
        User u = userDao.findStuByPower(gradeId,PowerType.STU_CHECKER);
        if(u == null)
            throw new TransactionalException("班级暂无考勤人");
        u.setType(UserType.STUDENT);
        Date termStart = termDao.getTermStart(termId);//获取开学日期
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(termStart);
        long time = termStart.getTime();
        int theWeek = calendar.get(Calendar.DAY_OF_WEEK);//获取星期几
        if(theWeek == 0)
            theWeek = 7;
        else
            theWeek -=1;
        List<Times> timesList = timesDao.getTermTimes(termId);
        for(TaskInfo task:taskInfos) {
            task.setChecker(u);
            //添加任务
            boolean startFound = false;//是否已经找到start
            boolean endFound = false;//是否已经找到end
            for(Times t:timesList) {
                if (!startFound)
                {
                    if(task.getStart().getSession() == t.getSession())
                    {
                        task.setStart(t);
                        startFound = true;
                    }
                }else {
                    if(task.getEnd().getSession() == t.getSession())
                    {
                        task.setEnd(t);
                        endFound = true;
                    }
                }
            }
            //如果没找到开始节次或结束节次，则抛出异常
            if(!startFound || !endFound)
                throw new TransactionalException("上课节次错误");
            taskDao.addTask(task,termId, type, gradeId);
            for(TaskCompletion comp:task.getCompletions())
            {
                Date d = new Date();
                Long taskday = Long.valueOf(task.getWeekday());
                Long compWeek = Long.valueOf(comp.getWeek());
                d.setTime(time+((taskday -Long.valueOf(theWeek))+(compWeek-1l)*7l)*24l*60l*60l*1000l);
                comp.setDate(d);
            }
        }
        taskCompletionDao.addComp(taskInfos);
    }

}
