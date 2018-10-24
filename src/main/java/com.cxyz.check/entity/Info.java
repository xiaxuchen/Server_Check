package com.cxyz.check.entity;

/**
 * Created by 夏旭晨 on 2018/9/23.
 */

public class Info {
    private int _id;//聊天记录编号(暂时无用)
    private OldUser sender;//发送者
    private OldUser receiver;//接受者
    private String content;//内容
    private int state;//状态，是否已读，已删除之类的

    @Override
    public String toString() {
        return "Info{" +
                "_id=" + _id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", state=" + state +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public OldUser getSender() {
        return sender;
    }

    public void setSender(OldUser sender) {
        this.sender = sender;
    }

    public OldUser getReceiver() {
        return receiver;
    }

    public void setReceiver(OldUser receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
