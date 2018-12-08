package com.cxyz.check.dto;


import com.cxyz.check.custom.ResultCustom;
import com.cxyz.check.json.CustomTimeStampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2018/12/5.
 */

public class CheckHistoryDto {

    /**
     * 任务id
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 考勤结果的状态
     */
    private Integer state;
    /**
     * 考勤结果
     */
    private List<ResultCustom> results;

    /**
     * 提交时间
     */
    private Timestamp commitTime;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;

    }

    @JsonSerialize(using= CustomTimeStampSerializer.class)
    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    public List<ResultCustom> getResults() {
        return results;
    }

    public void setResults(List<ResultCustom> results) {
        this.results = results;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CheckHistoryDto{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", state=" + state +
                ", results=" + results +
                ", commitTime=" + commitTime +
                '}';
    }

}
