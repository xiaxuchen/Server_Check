package com.cxyz.check.handler;

import com.cxyz.check.dto.CheckResult;
import com.cxyz.check.dto.VacateDto;
import com.cxyz.check.entity.Audit;
import com.cxyz.check.entity.Photo;
import com.cxyz.check.entity.User;
import com.cxyz.check.entity.Vacate;
import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.exception.vacate.PhotoDeleteException;
import com.cxyz.check.service.VacateService;
import com.cxyz.check.util.filepath.HashPathUtil;
import com.cxyz.check.util.parse.GsonUtil;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/vacate")
public class VacateHandler {

    @Autowired
    private VacateService vacateService;

    @RequestMapping(value = "/vacate",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8;"

    )
    @ResponseBody
    public CheckResult<String> vacate(@RequestParam String start,@RequestParam String end,
                                      @RequestParam String des,@RequestParam Integer len,
                                      @RequestParam Integer type,@RequestParam String sponsorId,
                                      @RequestParam Integer sponsorType)
    {
        CheckResult<String> cr = new CheckResult<>();
        try {
            Vacate vacate  = new Vacate();
            User user = new User(sponsorId);
            user.setType(sponsorType);
            vacate.setSponsor(user);
            vacate.setDes(des);
            vacate.setStart((Timestamp) GsonUtil.fromJson(start, Timestamp.class));
            vacate.setEnd((Timestamp) GsonUtil.fromJson(end, Timestamp.class));
            vacate.setLen(len);
            vacate.setType(type);
            System.out.println(vacate);
            vacateService.vacate(vacate);
            cr.setData("提交成功");
        }catch (GsonException e)
        {
            cr.setError("请假数据异常");
        }catch (Exception e)
        {
            cr.setError("提交失败");
            e.printStackTrace();
        }
        return cr;
    }

    /**
     * 审核请假
     * @param id
     * @param state
     * @param info
     * @param power
     * @return
     */
    @RequestMapping(value = "/auditVac",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8;")

    @ResponseBody
    public CheckResult<String> auditVac(@RequestParam Integer id,@RequestParam Integer state,
                                           @RequestParam(required = false) String info,@RequestParam Integer power)
    {
        CheckResult<String> cr = new CheckResult<>();

        try {
            Audit audit = new Audit();
            audit.setInfo(info);
            audit.setState(state);
            User checker = new User();
            checker.setPower(power);
            audit.setChecker(checker);
            audit.setId(id);
            vacateService.auditVac(audit);
            cr.setData("审核成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            cr.setError("服务器异常");
        }
        return cr;
    }

    /**
     * 获取请假信息
     * @param sponsorId 请假人id
     * @param sponsorType 请假人类型
     * @param state 请假状态
     * @return
     */
    @RequestMapping(value = "/getVacates",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8;")
    @ResponseBody
    public CheckResult<List<VacateDto>> getVacates(@RequestParam String sponsorId,@RequestParam Integer sponsorType,@RequestParam(required = false)Integer state)
    {
        CheckResult<List<VacateDto>> cr = new CheckResult<>();
        cr.setData(vacateService.getVacates(sponsorId,sponsorType,state));
        return cr;
    }


    /**
     * 获取请假信息
     * @param checkerId 审核人id
     * @param state 请假状态
     * @return
     */
    @RequestMapping(value = "/getVacatesToAudit",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8;")
    @ResponseBody
    public CheckResult<List<VacateDto>> getVacatesToAudit(@RequestParam String checkerId,@RequestParam(required = false)Integer state)
    {
        CheckResult<List<VacateDto>> cr = new CheckResult<>();
        cr.setData(vacateService.getVacatesToAudit(checkerId,state));
        return cr;
    }

    @RequestMapping(value = "/uploadVacate",
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public CheckResult<String> uploadVacate(HttpServletRequest request,@RequestParam(required = false) MultipartFile files[], @RequestParam String vacateDto)
    {
        System.out.println(vacateDto);
        CheckResult<String> cr = new CheckResult<>();
        VacateDto dto;
        try {
            dto = (VacateDto) GsonUtil.fromJson(vacateDto,new TypeToken<VacateDto>(){}.getType());
        } catch (GsonException e) {
            e.printStackTrace();
            cr.setError("服务器异常");
            return cr;
        }
        if(files != null)
        {
            ArrayList<Photo> photos = new ArrayList<>();
            ArrayList<File> temp = new ArrayList<>();
            String path = request.getServletContext().getRealPath("/WEB-INF/photo");
            try {
                for (MultipartFile f:files)
                {
                    String relativePath = HashPathUtil.getPath(f.getOriginalFilename());
                    String allPath = path+"/"+relativePath;
                    File parent = new File(allPath.substring(0,
                            allPath.lastIndexOf("/") + 1));
                    if(!parent.exists())
                        parent.mkdirs();
                    File out = new File(path,relativePath);
                    System.out.println(out.getAbsolutePath());
                    FileCopyUtils.copy(f.getInputStream(),new FileOutputStream(out));
                    photos.add(new Photo(relativePath));
                    temp.add(out);
                }
                dto.setPhotos(photos);
                vacateService.uploadVacate(dto);
                cr.setData("上传成功");
            } catch (IOException e) {
                //如果出现异常，删除已保存的图片
                if(!temp.isEmpty())
                {
                    for(File f:temp)
                    {
                        if(f.exists())
                            f.delete();
                    }
                }
                e.printStackTrace();
                cr.setError("服务器异常");
            }
        }
        return cr;
    }

    /**
     * 假删除图片
     * @param id 图片id
     * @return
     */
    @RequestMapping(value = "/deletePhoto",
        produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public CheckResult deletePhoto(@RequestParam Integer id)
    {
        CheckResult cr = new CheckResult();
        try {
            vacateService.deletePhoto(id);
            cr.setSuccess(true);
        }catch (PhotoDeleteException e)
        {
            e.printStackTrace();
            cr.setSuccess(false);
        }
        return cr;
    }


    /**
     * 假删除图片
     * @param id 图片id
     * @return
     */
    @RequestMapping(value = "/uploadPhoto",
            produces = "application/json;charset=utf-8"
    )
    @ResponseBody
    public CheckResult<List<Photo>> uploadPhotos(@RequestParam Integer id,@RequestParam MultipartFile files[],HttpServletRequest request)
    {
        CheckResult cr = new CheckResult();
        if(files != null)
        {
            ArrayList<String> photos = new ArrayList<>();
            ArrayList<File> temp = new ArrayList<>();
            String path = request.getServletContext().getRealPath("/WEB-INF/photo");
            try {
                for (MultipartFile f : files) {
                    String relativePath = HashPathUtil.getPath(f.getOriginalFilename());
                    String allPath = path + "/" + relativePath;
                    File parent = new File(allPath.substring(0,
                            allPath.lastIndexOf("/") + 1));
                    if (!parent.exists())
                        parent.mkdirs();
                    File out = new File(path, relativePath);
                    System.out.println(out.getAbsolutePath());
                    FileCopyUtils.copy(f.getInputStream(), new FileOutputStream(out));
                    photos.add(relativePath);
                    temp.add(out);
                }
                cr.setData(vacateService.uploadPhotos(photos,id));
            }catch (IOException e)
            {
                //如果出现异常，删除已保存的图片
                if(!temp.isEmpty())
                {
                    for(File f:temp)
                    {
                        if(f.exists())
                            f.delete();
                    }
                }
                e.printStackTrace();
                cr.setError("上传失败");
            }
        }
        return cr;
    }
}
