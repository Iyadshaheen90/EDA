package com.COMP490.EDA.Memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Memento> mementoList = new ArrayList<>();

    public void add(Memento state){
        mementoList.add(state);
    }

    public Memento get(int index){
        return mementoList.get(index);
    }

    public List<Memento> getMementoList() {
        return mementoList;
    }

    public void resetMementoList(int index) {
        mementoList = new ArrayList<>(mementoList.subList(0, index + 1));
    }
}
