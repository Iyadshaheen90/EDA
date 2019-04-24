package com.COMP490.EDA.Memento;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class StateHandler {
    private int stateNum = 0;
    private static Originator originator = new Originator();
    private static CareTaker careTaker = new CareTaker();

    public StateHandler() {}

    public int getStateNumber() {
        return stateNum;
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
}
