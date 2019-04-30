package com.COMP490.EDA.Memento;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class StateHandler {
    private int stateNum = -1;  // -1 takes care of the initial state of the pane
    private static Originator originator = new Originator();
    private static CareTaker careTaker = new CareTaker();

    public StateHandler(ArrayList<Shape> shapes) {
        save(shapes);
    }

    // Saves the state of the Pane
    public void save(ArrayList<Shape> shapes) {
        ArrayList<Shape> temp = new ArrayList<>(shapes);
        originator.setState(temp);
        careTaker.add(originator.saveStateToMemento());
        stateNum++;
    }

    public ArrayList<Shape> undo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum - 1));
            stateNum--;
//            System.out.println("In statehandler undo " + originator.getState());
            return originator.getState();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't undo further");
        }
        return null;
    }

    public ArrayList<Shape> redo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum + 1));
            stateNum++;
//            System.out.println("In statehandler redo " + originator.getState());
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