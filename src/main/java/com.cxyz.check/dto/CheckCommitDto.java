package com.cxyz.check.dto;

import com.cxyz.check.dto.normal.RecordDto;

import java.io.Serializable;
import java.util.List;

public class CheckCommitDto implements Serializable {

    /**
     * 关联的TaskCompletion的id
     */
    private int compId;

    /**
     * 考勤结果类型，在RecordType中取
     */
    private int type;

    /**
     * 考勤记录
     */
    private List<RecordDto> recordDtos;

    /**
     * 特殊情况说明
     */
    private String otherDes;

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<RecordDto> getRecordDtos() {
        return recordDtos;
    }

    public void setRecordDtos(List<RecordDto> recordDtos) {
        this.recordDtos = recordDtos;
    }

    public String getOtherDes() {
        return otherDes;
    }

    public void setOtherDes(String otherDes) {
        this.otherDes = otherDes;
    }

    @Override
    public String toString() {
        return "CheckDto{" +
                "compId=" + compId +
                ", type=" + type +
                ", recordDtos=" + recordDtos +
                ", otherDes='" + otherDes + '\'' +
                '}';
    }
}
