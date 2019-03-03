package com.cxyz.check.util.collections.interfaces;

public interface ForEachWithIndex<T> {

    /**
     * 执行操作
     * @param index 索引
     * @param item 元素
     */
    void doForEach(int index,T item);
}
