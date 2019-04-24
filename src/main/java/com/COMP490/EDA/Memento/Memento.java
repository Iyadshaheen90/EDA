package com.COMP490.EDA.Memento;

import javafx.scene.layout.Pane;

public class Memento {
    private Pane state;

    public Memento(Pane state){
        this.state = state;
    }

    public Pane getState(){
        return state;
    }
}