package com.cxyz.check.custom;

public class ResultCustom {

    private Integer resultType;

    private Integer count;

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ResultCustom{" +
                "resultType=" + resultType +
                ", count=" + count +
                '}';
    }
}
