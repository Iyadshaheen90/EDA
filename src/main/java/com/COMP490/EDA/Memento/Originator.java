package com.COMP490.EDA.Memento;

import javafx.scene.Node;

import java.util.ArrayList;

public class Originator {
    private ArrayList<Node> state;

    public void setState(ArrayList<Node> state){
        this.state = state;
    }

    public ArrayList<Node> getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
