package com.COMP490.EDA.Memento;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class StateHandler {
    private int stateNum = -1;  // -1 takes care of the initial state of the pane
    private static Originator originator = new Originator();
    private static CareTaker careTaker = new CareTaker();

    public StateHandler(Pane pane) {
        save(pane);
    }

    // Saves the state of the Pane
    public void save(Pane pane) {
        ArrayList<Node> temp = new ArrayList<>();
        for(Node node : pane.getChildren()) {
            temp.add(node);
        }
        originator.setState(temp);
        careTaker.add(originator.saveStateToMemento());
        stateNum++;
    }

    public ArrayList<Node> undo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum - 1));
            stateNum--;
            return originator.getState();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't undo further");
        }
        return null;
    }

    public ArrayList<Node> redo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum + 1));
            stateNum++;
            return originator.getState();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't redo further");
        }
        return null;
    }

    public int getStateNum() {
        return stateNum;
    }

    @Override
    public String toString() {
        String temp = "State number: " + stateNum;
        return temp;
    }
}