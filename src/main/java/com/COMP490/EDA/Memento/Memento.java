package com.COMP490.EDA.Memento;

import javafx.scene.Node;

import java.util.ArrayList;

public class Memento {
    private ArrayList<Node> state;

    public Memento(ArrayList<Node> state){
        this.state = state;
    }

    public ArrayList<Node> getState(){
        return state;
    }
}