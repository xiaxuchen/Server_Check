package com.cxyz.check.dto.normal;

import java.io.Serializable;

public class RecordDto implements Serializable {

    /**
     * 所属学生
     */
    private String id;

    /**
     * 考勤结果，在ResultType中取
     */
    private Integer result;

    /**
     * 对结果的描述
     */
    private String des;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "id='" + id + '\'' +
                ", result=" + result +
                ", des='" + des + '\'' +
                '}';
    }
}
