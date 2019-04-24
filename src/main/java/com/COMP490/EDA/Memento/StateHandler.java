package com.COMP490.EDA.Memento;

import javafx.scene.layout.Pane;

public class StateHandler {
    private int stateNum = 0;
    private static Originator originator = new Originator();
    private static CareTaker careTaker = new CareTaker();

    public StateHandler() {}

    // Saves the state of the Pane
    public void save(Pane pane) {
        Pane temp = new Pane();
        temp.getChildren().addAll(pane.getChildren());
        originator.setState(temp);
        careTaker.add(originator.saveStateToMemento());
        stateNum++;
    }

    public Pane undo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum - 1));
            stateNum--;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't undo further");
        }
        return originator.getState();
    }

    public Pane redo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum + 1));
            stateNum++;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't redo further");
        }
        return originator.getState();
    }
}
