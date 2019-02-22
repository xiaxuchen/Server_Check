package com.cxyz.check.service.impl;

import com.cxyz.check.dao.AuditDao;
import com.cxyz.check.dao.CollegeDao;
import com.cxyz.check.dao.GradeDao;
import com.cxyz.check.dao.PushDao;
import com.cxyz.check.dao.UserDao;
import com.cxyz.check.dao.VacateDao;
import com.cxyz.check.dto.VacateDto;
import com.cxyz.check.entity.Audit;
import com.cxyz.check.entity.Photo;
import com.cxyz.check.entity.Push;
import com.cxyz.check.entity.User;
import com.cxyz.check.entity.Vacate;
import com.cxyz.check.exception.audit.AuditedException;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.exception.vacate.PhotoDeleteException;
import com.cxyz.check.exception.vacate.PhotoUploadException;
import com.cxyz.check.exception.vacate.VacateException;
import com.cxyz.check.exception.vacate.VacateFailException;
import com.cxyz.check.service.VacateService;
import com.cxyz.check.typevalue.NotifyType;
import com.cxyz.check.typevalue.PowerType;
import com.cxyz.check.typevalue.UserType;
import com.cxyz.check.util.parse.GsonUtil;
import com.cxyz.check.util.push.PushUtil;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class VacateServiceImpl implements VacateService {

    @Autowired
    private VacateDao vacateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private AuditDao auditDao;

    @Autowired
    private PushDao pushDao;



    @Override
    @Transactional
    public void vacate(Vacate vacate) {

        //计算请假时长,推送给老师
        int count = vacateDao.addVacate(vacate);

        if(count == 0)
            throw new VacateFailException("插入失败");
        //推送，添加审核名单
        Set<User> users = new HashSet<>();

        Integer gradeId = gradeDao.getGradeId(vacate.getSponsor().getId(),
                vacate.getSponsor().getType());

        users.add(new User(gradeDao.getManagerId(gradeId), UserType.TEACHER));

        userDao.getTeaIds(gradeDao.getCollegeId(gradeId), PowerType.TEA_AUXILIARY).forEach(
           s -> {
               users.add(new User(s,UserType.TEACHER));
           }
        );


        System.out.println(users);
        System.out.println(vacate.getLen());
        if(vacate.getLen()>=3)
        {
            userDao.getTeaIds(gradeDao.getCollegeId(gradeId), PowerType.TEA_SECRETARY).forEach(
                    s -> {
                        users.add(new User(s,UserType.TEACHER));
                    }
            );
        }

        Set<Audit> audits = new HashSet<>();
        ArrayList<Push> pushes = new ArrayList<>();
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put("type",NotifyType.VACATION_AUDIT+"");
            final String info = GsonUtil.toJson(map);
            users.forEach(u -> {
                Audit audit = new Audit();
                audit.setChecker(u);
                audit.setVac(vacate);
                audits.add(audit);
                Push push = new Push();
                push.setInfo(info);
                push.setReceiver(u);
                pushes.add(push);
            });

            pushDao.addPushes(pushes);
        } catch (GsonException e) {
            e.printStackTrace();
        }
        auditDao.addAudits(audits);
        System.out.println(users);


//        PushUtil.jpushAndroid("请假审核","type",NotifyType.
//                VACATION_AUDIT+"",ids);
    }

    @Transactional
    @Override
    public void auditVac(Audit audit) {
        int auditCount = auditDao.auditVac(audit);
        System.out.println(auditCount);
        if(auditCount == 0)
            throw new AuditedException("请假已审核");
        String sponsorId = vacateDao.getVacateSponsorId
                (auditDao.getVacId(audit.getId()));
        ArrayList<Push> pushes = new ArrayList<>();
        Push push = new Push();
        push.setReceiver(new User(sponsorId,UserType.STUDENT));
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put("type",NotifyType.VACATION+"");
            push.setInfo(GsonUtil.toJson(map));
        } catch (GsonException e) {
            e.printStackTrace();
        }
        pushes.add(push);
        pushDao.addPushes(pushes);
        //PushUtil.jpushAndroid("有最新请假消息","type",NotifyType.VACATION+"",sponsorId);
        auditDao.clearOther(audit.getId(),audit.getChecker().getPower());

    }

    @Override
    public List<VacateDto> getVacates(String sponsorId, Integer sponsorType, Integer state) {
        return vacateDao.getVacates(sponsorId, sponsorType, state);
    }

    @Override
    public List<VacateDto> getVacatesToAudit(String checkerId, Integer state) {
        return vacateDao.getVacatesToAudit(checkerId,state);
    }

    @Transactional
    @Override
    public void uploadVacate(VacateDto dto) {
        vacateDao.addVacate(dto);

        if(dto.getTimeType() == VacateDto.WEEK)
        {
            vacateDao.addDates(dto.getDates(),dto.getId());
        }

        if(dto.getPhotos() != null && !dto.getPhotos().isEmpty())
        {
            vacateDao.uploadPhotos(dto.getPhotos(),dto.getId());
        }
    }

    @Override
    public void deletePhoto(Integer id) {
        final int count = vacateDao.deletePhoto(id);
        if(count == 0)
            throw new PhotoDeleteException("删除失败");
    }

    @Transactional
    @Override
    public List<Photo> uploadPhotos(List<String> paths, Integer vacId) {
        List<Photo> photos = new ArrayList<>();
        for(String path:paths)
        {
            Photo p = new Photo();
            p.setUri(path);
            int count = vacateDao.uploadPhoto(p,vacId);
            if(count == 0)
                throw new PhotoUploadException("上传失败");
            photos.add(p);
        }
        return photos;
    }
}
