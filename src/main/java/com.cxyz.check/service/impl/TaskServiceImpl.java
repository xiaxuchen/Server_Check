package com.cxyz.check.service.impl;

import com.cxyz.check.dao.CollegeDao;
import com.cxyz.check.dao.GradeDao;
import com.cxyz.check.dao.LessonDao;
import com.cxyz.check.dao.PushDao;
import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dao.RoomDao;
import com.cxyz.check.dao.SchoolDao;
import com.cxyz.check.dao.TaskCompletionDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dao.TermDao;
import com.cxyz.check.dao.TimesDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.CheckTaskDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.GradeStusDto;
import com.cxyz.check.dto.GradeLessonDto;
import com.cxyz.check.dto.LessonDto;
import com.cxyz.check.dto.SubjectDto;
import com.cxyz.check.entity.Lesson;
import com.cxyz.check.entity.Push;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.TaskInfo;
import com.cxyz.check.entity.Times;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.GradeNotFoundException;
import com.cxyz.check.exception.record.RecordException;
import com.cxyz.check.exception.task.NoTaskException;
import com.cxyz.check.exception.transaction.CommitCheckFailException;
import com.cxyz.check.exception.transaction.TransactionalException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.service.TaskService;
import com.cxyz.check.typevalue.NotifyType;
import com.cxyz.check.typevalue.PowerType;
import com.cxyz.check.typevalue.TaskCompletionState;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.automapper.AutoMapper;
import com.cxyz.check.util.date.DateTime;
import com.cxyz.check.util.excel.ExcelUtil;
import com.cxyz.check.util.parse.GsonUtil;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private CollegeDao collegeDao;

    @Autowired
    private LessonDao lessonDao;

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

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private PushDao pushDao;

    @Override
    public CheckTaskDto checkTask(String checker_id, int checker_type, int type) throws NoTaskException {
        //TODO 这里用作测试
        DateTime dateTime = DateTime.fromUDate(new Date());
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
                for(CommitCheckDto.StuInfo s:checkDto.getStuInfos())
                {
                    Map<String,String> map = new HashMap<>();
                    map.put("type", NotifyType.BAD_CHECK_RECORD+"");
                    Push push = new Push();
                    try {
                        push.setInfo(GsonUtil.toJson(map));
                        push.setReceiver(new User(s.getId(),UserType.STUDENT));
                        List<Push> pushes = new ArrayList<>();
                        pushes.add(push);
                        pushDao.addPushes(pushes);
                    } catch (GsonException e) {
                        e.printStackTrace();
                    }
                    //PushUtil.jpushAndroid("考勤异常",map,s.getId());
                }
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

    /**
     * TODO 暂时废弃，被addLesson替代
     * @param taskInfos 考勤任务
     * @param type
     * @param gradeId
     */
    @Override
    @Transactional
    @Deprecated
    public void addTask(List<TaskInfo> taskInfos,Integer type,Integer gradeId) {
        int termId = schoolDao.getCurrentTermId(gradeDao.getGradeSchoolId(gradeId));
        gradeDao.addTasks(gradeId,termId);
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
        long theWeek = calendar.get(Calendar.DAY_OF_WEEK);//获取星期几
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
                System.out.println(d.toLocaleString());
                System.out.println(com.cxyz.check.util.date.Date.fromUDate(d).getDate());
                comp.setDate(d);
            }
        }
        taskCompletionDao.addComp(taskInfos);
    }

    @Override
    public List<SubjectDto> getSubjects(Integer gradeId) {
        //获取当前学期
        int termId = schoolDao.getCurrentTermId(gradeDao.getGradeSchoolId(gradeId));
        List<TaskInfo> taskInfos = taskDao.getSubjects(gradeId, termId);
        System.out.println(taskInfos);
        if(taskInfos == null)
            return null;
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for(TaskInfo t:taskInfos)
        {
            SubjectDto s = new SubjectDto();
            s.setId(t.getId());
            s.setName(t.getName());
            if(t.getRoom()!=null)
                s.setRoom(roomDao.getRoomName(t.getRoom().getId()));
            s.setDay(t.getWeekday());
            s.setStart(timesDao.getSession(t.getStart().getId()));
            s.setStep(timesDao.getSession(t.getEnd().getId())-s.getStart()+1);
            if(t.getSponsor()!=null)
                s.setTeacher(t.getSponsor().getName());
            List<Integer> weeks = new ArrayList<>();
            for(TaskCompletion comp:t.getCompletions())
            {
                weeks.add(comp.getWeek());
            }
            s.setWeekList(weeks);
            subjectDtos.add(s);
        }
        System.out.println(subjectDtos);
        return subjectDtos;
    }

    @Override
    public Workbook getStatisticExcel( String sponsorId, Integer sponsorType, Integer lessonId) {
        Lesson lesson = lessonDao.getLesson(sponsorId, sponsorType, lessonId);
        //查询信息
        if(lesson == null || lesson.getTasks().isEmpty())
            throw new RecordException("当前暂无考勤记录");
        int gradeId = lesson.getGrade().getId();
        List<User> users = userDao.selectStusByGrade(gradeId);
        String gradeName = gradeDao.getGradeName(gradeId);
        return ExcelUtil.getCheckExcel(lesson,users,gradeName);
    }

    @Override
    public List<GradeLessonDto> getGradeTasks(String sponsorId, Integer sponsorType, Integer termId) {
        if(termId == null)
            termId = schoolDao.getCurrentTermId(userDao.getSchoolId(sponsorId,sponsorType));
        List<GradeLessonDto> gradeTasks = taskDao.getGradeTasks(sponsorId, sponsorType, termId);
        for(GradeLessonDto dto:gradeTasks)
        {
            for(LessonDto lessonDto:dto.getLessons())
            {
                lessonDto.setCheckerName(userDao.getName(lessonDto.getCheckerId(),lessonDto.getCheckerType()));
            }
        }
        return gradeTasks;
    }


}
