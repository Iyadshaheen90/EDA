package com.COMP490.EDA.Memento;

import javafx.scene.layout.Pane;

public class Originator {
    private Pane state;

    public void setState(Pane state){
        this.state = state;
    }

    public Pane getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
