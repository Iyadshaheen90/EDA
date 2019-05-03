package com.COMP490.EDA.Memento;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Memento {
    private ArrayList<Shape> state;

    public Memento(ArrayList<Shape> state){
        this.state = state;
    }

    public ArrayList<Shape> getState(){
        return state;
    }

    @Override
    public String toString() {
        return state.toString();
    }
}