package com.cxyz.check.util.collections;

import com.cxyz.check.util.collections.interfaces.ForEach;
import com.cxyz.check.util.collections.interfaces.ForEachWithIndex;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class List<T> implements java.util.List<T> {

    private java.util.List<T> list;

    public List()
    {
        this(new ArrayList<>());
    }

    public List(java.util.List<T> list)
    {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index,c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index,element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index,element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public java.util.List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex,toIndex);
    }

    /**
     * forEach循环
     * @param consumer 操作
     */
    public void forEach(ForEachWithIndex<T> consumer){
        int index = 0;
        for(;index<size();index++)
            consumer.doForEach(index,get(index));
    }

    /**
     * forEach循环
     * @param consumer 操作
     */
    public void forEach(ForEach<T> consumer){
        for(T item:list)
            consumer.doForEach(item);
    }
}
