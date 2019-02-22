package com.cxyz.check.service.impl;

import com.cxyz.check.dao.CollegeDao;
import com.cxyz.check.dao.EnvirDao;
import com.cxyz.check.dao.GradeDao;
import com.cxyz.check.dao.SchoolDao;
import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.GradeDto;
import com.cxyz.check.entity.School;
import com.cxyz.check.entity.Term;
import com.cxyz.check.entity.Times;
import com.cxyz.check.exception.envir.LessonImportedException;
import com.cxyz.check.exception.envir.TermExistException;
import com.cxyz.check.exception.envir.UserImportedException;
import com.cxyz.check.service.EnvirService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EnvirServiceImpl implements EnvirService {

    @Autowired
    private EnvirDao envirDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private CollegeDao collegeDao;

    Calendar instance = Calendar.getInstance();

    @Override
    public void addTerm(AddTermDto dto) {
        final int count = envirDao.checkTerm
                (dto.getSchool(),dto.getDate(),dto.getTerm());
        System.out.println(count);
        if(count != 0)
            throw new TermExistException();

        Term term = new Term();
        String date[] = dto.getDate().split("-");
        instance.clear();
        instance.set(Integer.parseInt(date[0]),
                Integer.parseInt(date[1])-1,
                Integer.parseInt(date[2]));
        term.setBegin(instance.getTime());
        term.setNum(dto.getTerm());
        term.setSchool(new School(dto.getSchool()));
        List<Times> timesList = new ArrayList<>();
        Times times;
        int i =1;
        for(String t:dto.getTimes())
        {
            times = new Times();
            //开始和结束时间
            String start_end[] = t.split(" - ");
            String start[] = start_end[0].split(":");
            String end[] = start_end[1].split(":");
            times.setStart(getTime(start));
            times.setEnd(getTime(end));
            times.setSession(i);
            timesList.add(times);
            i++;
        }

        System.out.println(timesList);

        envirDao.addTerm(term);

        System.out.println(term.getId());

        envirDao.addTimes(timesList,term.getId());

    }

    /**
     * 将字符串转化为timestamp
     * @param time
     * @return
     */
    private Timestamp getTime(String time[])
    {
        instance.clear();
        instance.set(1970,0,0,
                Integer.parseInt(time[0]),Integer.parseInt(time[1]),0);
        return new Timestamp(instance.getTimeInMillis());
    }

    @Override
    public List<GradeDto> getCollegeGrades(Integer collegeId)
    {
        return gradeDao.getCollegeGrades(collegeId);
    }

    @Override
    public boolean isUserImportEnable(int gradeId) {
        int result = gradeDao.isStuImportEnable(gradeId);
        System.out.println(result);
        if(result == 0)
            throw new UserImportedException("学生信息已导入");
        if(result == 1)
            return true;
        return false;
    }

    @Override
    public boolean isLessonImportEnable(int gradeId) {
        int result = gradeDao.isLessonImportEnable(gradeId,schoolDao.getCurrentTermId(gradeDao.getGradeSchoolId(gradeId)));
        if(result == 0)
            throw new LessonImportedException("课程信息已导入");
        if(result == 1)
            return true;
        return false;
    }

    @Override
    public void toggleUserImport(int collegeId) {
        collegeDao.toggleUserImport(collegeId);
    }

    @Override
    public void toggleLessonImport(int collegeId) {
        collegeDao.toggleLessonImport(collegeId);
    }

    @Override
    public boolean isCollegeUserImportEnable(int collegeId) {
        return collegeDao.isUserImportEnable(collegeId);
    }

    @Override
    public boolean isCollegeLessonImportEnable(int collegeId) {
        return collegeDao.isLessonImportEnable(collegeId);
    }

}
