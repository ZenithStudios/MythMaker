package com.elixer.core.Util;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by aweso on 11/13/2017.
 */
public class PriorityList<T> implements Iterable<T>, Iterator<T> {

    private LinkedHashMap<Integer, T> data;
    private ArrayList<Integer> priorityList = new ArrayList<>();

    private int count = 0;

    public PriorityList() {
        data = new LinkedHashMap<Integer, T>();
    }

    public PriorityList(int size) {
        data = new LinkedHashMap<Integer, T>(size);
    }

    public void insert(int priority, T node) {

    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if(count < data.size())
            return true;
        return false;
    }

    @Override
    public T next() {
        if(count >= data.size())
            throw new NoSuchElementException();
        return data.get(count++);
    }
}
