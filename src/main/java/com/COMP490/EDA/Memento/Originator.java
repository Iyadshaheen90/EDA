package com.COMP490.EDA.Memento;


import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Originator {
    private ArrayList<Shape> state;

    public void setState(ArrayList<Shape> state){
        this.state = state;
    }

    public ArrayList<Shape> getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
