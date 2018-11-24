package com.cxyz.check.dto;


import com.cxyz.check.entity.CheckRecord;
import com.cxyz.check.json.CustomDateSerializer;
import com.cxyz.check.json.CustomTimeStampSerializer;
import com.cxyz.check.typevalue.CheckRecordResult;
import com.cxyz.check.util.automapper.annotation.Classes;
import com.cxyz.check.util.automapper.annotation.Path;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/11/11.
 */


public class CheckRecordDto {

    //总考勤次数
    private int all;

    //请假
    private int vacate;

    //迟到
    private int late;

    //缺勤
    private int absenteeism;

    //早退
    private int earlyeave;

    //考勤详情
    private List<RecordInfo> recordInfos;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getVacate() {
        return vacate;
    }

    public int getLate() {
        return late;
    }

    public int getAbsenteeism() {
        return absenteeism;
    }

    public int getEarlyeave() {
        return earlyeave;
    }

    public List<RecordInfo> getRecordInfos() {
        return recordInfos;
    }

    public void setRecordInfos(List<RecordInfo> recordInfos) {
        this.recordInfos = recordInfos;
        reInit();
        for(RecordInfo ri:recordInfos)
        {
            switch (ri.getResult()) {
                case CheckRecordResult.ABSENTEEISM: {
                    absenteeism++;
                }
                break;
                case CheckRecordResult.EARLYLEAVE: {
                    earlyeave++;
                }
                break;
                case CheckRecordResult.LATE: {
                    late++;
                }
                break;
                case CheckRecordResult.VACATE: {
                    vacate++;
                }
                break;
            }
        }
    }

    //重置
    private void reInit()
    {
        late = 0;
        vacate = 0;
        absenteeism = 0;
        earlyeave = 0;
    }

    @Override
    public String toString() {
        return "CheckRecordDto{" +
                "all=" + all +
                ", vacate=" + vacate +
                ", late=" + late +
                ", absenteeism=" + absenteeism +
                ", earlyeave=" + earlyeave +
                ", recordInfos=" + recordInfos +
                '}';
    }

    //考勤详细信息
    @Classes(CheckRecord.class)
    public static class RecordInfo{
        //描述信息
        private String des;
        //考勤结果
        private int result;
        //考勤时间
        @Path("completion.updateTime")
        private Timestamp date;
        //完成情况id
        @Path("completion.id")
        private Integer compId;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        @JsonSerialize(using= CustomDateSerializer.class)
        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public int getCompId() {
            return compId;
        }

        public void setCompId(int compId) {
            this.compId = compId;
        }

        @Override
        public String toString() {
            return "RecordInfo{" +
                    "des='" + des + '\'' +
                    ", result=" + result +
                    ", date=" + date +
                    ", compId=" + compId +
                    '}';
        }
    }
}
