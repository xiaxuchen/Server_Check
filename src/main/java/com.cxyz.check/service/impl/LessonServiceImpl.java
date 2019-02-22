package com.cxyz.check.service.impl;

import com.cxyz.check.custom.TaskInfoCustom;
import com.cxyz.check.dao.GradeDao;
import com.cxyz.check.dao.LessonDao;
import com.cxyz.check.dao.SchoolDao;
import com.cxyz.check.dao.TaskCompletionDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dao.TermDao;
import com.cxyz.check.dao.TimesDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.entity.Lesson;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.Times;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.service.LessonService;
import com.cxyz.check.typevalue.PowerType;
import com.cxyz.check.typevalue.UserType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private TaskCompletionDao taskCompletionDao;

    @Autowired
    private TimesDao timesDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TermDao termDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    @Transactional
    public void addLesson(List<TaskInfo> taskInfos,Integer type,Integer gradeId) {

        if(taskInfos == null || taskInfos.isEmpty())
            return;

        int termId = schoolDao.getCurrentTermId(gradeDao.getGradeSchoolId(gradeId));
        System.out.println("--------"+termId+"----------");
        gradeDao.addTasks(gradeId,termId);

        if(termId == 0)
            throw new TransactionalException("未知错误");

        //为任务设置考勤人
        User u = userDao.findStuByPower(gradeId, PowerType.STU_CHECKER);

        if(u == null)
            throw new TransactionalException("班级暂无考勤人");
        u.setType(UserType.STUDENT);

        Date termStart = termDao.getTermStart(termId);//获取开学日期
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(termStart);
        long time = termStart.getTime();
        long theWeek = calendar.get(Calendar.DAY_OF_WEEK);//获取星期几
        if(theWeek == 0)
            theWeek = 7;
        else
            theWeek -=1;
        List<Times> timesList = timesDao.getTermTimes(termId);


        Map<Integer,Integer> map = new HashMap<>();//用于判断是否已录入课程信息，同时保存自增的id
        for(TaskInfo taskinfo:taskInfos)
        {
            taskinfo.setTerm(new Term(termId));
            taskinfo.setChecker(u);
            boolean contains = false;
            for(Integer i:map.keySet())
            {
                Integer sign = ((TaskInfoCustom)taskinfo).getSign();
                System.out.println(sign);
                System.out.println(i);
                if(i.equals(sign)||i==sign)
                {
                    contains = true;
                    break;
                }
            }

            if(contains)
                continue;

            System.out.println(((TaskInfoCustom)taskinfo).getSign());
            System.out.println(map);
            lessonDao.addLesson(taskinfo);
            map.put(((TaskInfoCustom)taskinfo).getSign(),taskinfo.getLesson().getId());
        }

        /**
         * 给所有的相同课程设置自动生成的id
         */
        for(TaskInfo taskInfo:taskInfos)
        {
            taskInfo.setLesson(new Lesson(map.get(((TaskInfoCustom)taskInfo).getSign())));
            System.out.println(((TaskInfoCustom)taskInfo).toString());
        }



        for(TaskInfo task:taskInfos) {
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
                System.out.println(d.toLocaleString());
                System.out.println(com.cxyz.check.util.date.Date.fromUDate(d).getDate());
                comp.setDate(d);
            }
        }

        System.out.println(taskInfos);
        taskCompletionDao.addComp(taskInfos);
    }

    @Override
    public void updateLessonNum(Integer id, String num) {
        lessonDao.updateLessonNum(id,num);
    }
}
