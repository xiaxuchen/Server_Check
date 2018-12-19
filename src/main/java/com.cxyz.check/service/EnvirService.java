package com.cxyz.check.service;

import com.cxyz.check.dto.AddTermDto;
import com.cxyz.check.dto.GradeDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface EnvirService {

    /**
     * 添加学期
     * @param dto
     */
    void addTerm(AddTermDto dto);

    /**
     * 获取学院所有班级id和name
     * @param collegeId
     * @return
     */
    List<GradeDto> getCollegeGrades(Integer collegeId);

}
