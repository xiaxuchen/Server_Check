package com.cxyz.check.dto;

import java.util.List;

/**
 * Created by 鱼塘主 on 2018/11/21.
 */
//["2017-2018学年秋", "1", "高等数学", "2424", "壮飞", "2018-09-07","2019-1-21", "14:00", "14:45"]
public class SubjectsDto {
    private String id;
    private String name;
    private String tea_name;
    private String dataone;
    private String datatwo;
    private String start_data;
    private String end_data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTea_name() {
        return tea_name;
    }

    public void setTea_name(String tea_name) {
        this.tea_name = tea_name;
    }

    public String getDataone() {
        return dataone;
    }

    public void setDataone(String dataone) {
        this.dataone = dataone;
    }

    public String getDatatwo() {
        return datatwo;
    }

    public void setDatatwo(String datatwo) {
        this.datatwo = datatwo;
    }

    public String getStart_data() {
        return start_data;
    }

    public void setStart_data(String start_data) {
        this.start_data = start_data;
    }

    public String getEnd_data() {
        return end_data;
    }

    public void setEnd_data(String end_data) {
        this.end_data = end_data;
    }

    @Override
    public String toString() {
        return "SubjectsDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tea_name='" + tea_name + '\'' +
                ", dataone='" + dataone + '\'' +
                ", datatwo='" + datatwo + '\'' +
                ", start_data='" + start_data + '\'' +
                ", end_data='" + end_data + '\'' +
                '}';
    }
}
