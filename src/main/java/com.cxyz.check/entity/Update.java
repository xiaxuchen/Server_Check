package com.cxyz.check.entity;

import com.cxyz.check.util.date.DateTime;

/**
 * 更新记录表
 */
public class Update {
    //更新记录编号
    private Integer id;
    //更新人
    private  User updater;
    //更新原因
    private String des;
    //更新时间
    private DateTime update_time;
    //更新的记录
    private CheckRecord record;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUpdater() {
        return updater;
    }

    public void setUpdater(User updater) {
        this.updater = updater;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public DateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(DateTime update_time) {
        this.update_time = update_time;
    }

    public CheckRecord getRecord() {
        return record;
    }

    public void setRecord(CheckRecord record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Update{" +
                "id=" + id +
                ", updater=" + updater +
                ", des='" + des + '\'' +
                ", update_time=" + update_time +
                ", record=" + record +
                '}';
    }
}
