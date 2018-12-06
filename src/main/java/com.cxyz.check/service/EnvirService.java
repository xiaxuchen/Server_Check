package com.cxyz.check.service;

import com.cxyz.check.dto.AddTermDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

public interface EnvirService {

    /**
     * 添加学期
     * @param dto
     */
    void addTerm(AddTermDto dto);

}
