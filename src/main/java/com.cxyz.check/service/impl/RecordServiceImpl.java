package com.cxyz.check.service.impl;

import com.cxyz.check.custom.ResultCustom;
import com.cxyz.check.dao.PushDao;
import com.cxyz.check.dao.RecordDao;
import com.cxyz.check.dao.TaskCompletionDao;
import com.cxyz.check.dao.TaskDao;
import com.cxyz.check.dao.UpdateDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dto.AlterRecordDto;
import com.cxyz.check.dto.CheckHistoryDto;
import com.cxyz.check.dto.CheckRecordDto;
import com.cxyz.check.dto.CommitCheckDto;
import com.cxyz.check.dto.MyHistoryDto;
import com.cxyz.check.dto.StatisticDto;
import com.cxyz.check.dto.StatisticRecordDto;
import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.entity.Push;
import com.cxyz.check.entity.TaskCompletion;
import com.cxyz.check.entity.User;
import com.cxyz.check.exception.record.NOHistoryException;
import com.cxyz.check.exception.record.NoMoreHistoryException;
import com.cxyz.check.service.RecordService;
import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.automapper.AutoMapper;
import com.cxyz.check.util.push.PushUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.FocusTraversalPolicy;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

@Service
public class RecordServiceImpl implements RecordService {

    //一次加载考勤记录的条数
    private static final int LEN = 10;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskCompletionDao completionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UpdateDao updateDao;

    @Autowired
    private PushDao dao;


    @Override
    public List<MyHistoryDto> getMyHistory(String id, Integer result, int start) {
        List<MyHistoryDto> myHistory = recordDao.getMyHistory(id, result, start, LEN);
        if(myHistory.isEmpty())
            throw new NoMoreHistoryException();
        return myHistory;
    }

    @Override
    public CheckRecordDto getRecordStatistic(String id, int type, int grade) {
        List<ResultCustom> customs = recordDao.getRecordStatistic(id);
        CheckRecordDto checkRecordDto = new CheckRecordDto();
        checkRecordDto.setResults(customs);
        checkRecordDto.setAll(taskDao.getGradeCheck(grade));
        return checkRecordDto;
    }

    @Override
    public List<CheckHistoryDto> getHistory(String id, int type) {
        return loadMore(id,type,0);
    }

    @Override
    public List<CheckHistoryDto> loadMore(String id, int type, int start) throws NoMoreHistoryException {
        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("用户名有误!");
        List<CheckHistoryDto> history = recordDao.getHistory(id, type,start,LEN);
        if(history.size() == 0)
            throw new NoMoreHistoryException("＞▂＜ 已经到底了");
        return history;
    }

    @Override
    public List<AlterRecordDto> getAlterRecords(Integer gradeId, Integer compId) {
        List<AlterRecordDto> rds = recordDao.getAlterRecords(gradeId, compId);
        for(AlterRecordDto dto:rds)
        {
            if(dto.getResult()==null)
                dto.setResult(CheckRecordResult.NORMAL);
        }
        return rds;
    }

    @Transactional
    @Override
    public void updateRecords(Integer compId,List<AlterRecordDto> alterRecordDtos,String updaterId,Integer updaterType) {
        int count = 0;
        List<CheckRecord> records = new ArrayList<>();
        TaskCompletion completion = new TaskCompletion(compId);
        for (AlterRecordDto dto:alterRecordDtos)
        {
            //获取历史记录
            CheckRecord record = recordDao.getAlterRecord(dto.getStu().getId(),compId);
            //如果历史记录为空，则添加异常记录到tb_record
            if(record == null)
            {
                //tb_record中没有记录，则历史记录为正常
                record = new CheckRecord();
                record.setCompletion(completion);
                record.setResult(CheckRecordResult.NORMAL);

                User u = new User(dto.getStu().getId());
                record.setStu(u);
                CommitCheckDto.StuInfo info = new CommitCheckDto.StuInfo();
                info.setId(dto.getStu().getId());
                info.setResult(dto.getResult());
                info.setDes(dto.getDes());
                recordDao.addRecords(compId,new ArrayList(){{add(info);}});
            }
            //如果修改后的结果为正常，则将以前的记录从tb_record移除
            else if(dto.getResult() == CheckRecordResult.NORMAL)
            {
                recordDao.removeRecords(compId,new ArrayList(){{
                    CheckRecord record1 = new CheckRecord();
                    record1.setStu(new User(dto.getStu().getId()));
                    add(record1);
                }});
            }
            else
                count = recordDao.updateRecord(compId,dto);

            record.setCompletion(completion);
            records.add(record);
        }

        System.out.println(records.toString());

        //加入更新表
        updateDao.addUpdates(records,updaterId,updaterType);

        String compSponsorId = completionDao.getCompSponsorId(compId);
        if (compSponsorId != null)
        {
            //推送给任课老师
//            Push push = new Push();
//            push.setReceiver(new User(compSponsorId,UserType.TEACHER));
//            push.setInfo("");
        }
        //PushUtil.jpushAndroid("更新记录","compId",compId+"",compSponsorId);
    }

    @Override
    public StatisticDto getStatistic(String start, String end, Integer gradeId) {
        List<ResultCustom> results = recordDao.getResults(start, end, gradeId);
        final int count = userDao.gradeStuCount(gradeId);
        StatisticDto dto = new StatisticDto();
        dto.setResults(results);
        dto.setPersonCount(count);
        return dto;
    }

    @Override
    public List<StatisticRecordDto> getStatisticRecords(String start, String end, Integer gradeId, Integer resultType) {
        return recordDao.getStatisticRecords(start,end,gradeId,resultType);
    }

    @Override
    public List<CheckHistoryDto> getLessonHistories(Integer id,int start) {
        List<CheckHistoryDto> lessonHistories = recordDao.getLessonHistories(id, start, LEN);
        if(lessonHistories.size() == 0)
            throw new NoMoreHistoryException("没有更多历史记录了");
        return lessonHistories;
    }

}
