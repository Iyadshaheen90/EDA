package com.COMP490.EDA.Memento;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class StateHandler {
    private int stateNum = -1;  // -1 takes care of the initial state of the pane
    private static Originator originator = new Originator();
    private CareTaker careTaker = new CareTaker();
    private boolean close = false;

    public StateHandler(ArrayList<Shape> shapes) {
        save(shapes);
    }

    // Saves the state of the Pane
    public void save(ArrayList<Shape> shapes) {
        checkNewPath();
        ArrayList<Shape> temp = new ArrayList<>(shapes);
        originator.setState(temp);
        careTaker.add(originator.saveStateToMemento());
        stateNum++;
        System.out.println("Statenum: " + stateNum);
        this.close = checkClose();
    }

    public ArrayList<Shape> undo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum - 1));
            stateNum--;
//            System.out.println("In statehandler undo " + originator.getState());
            System.out.println("Statenum: " + stateNum);
            this.close = checkClose();
            return originator.getState();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't undo further");
        }
        return null;
    }
    public void reset(){
        this.close=false;
    }
    public ArrayList<Shape> redo() {
        try {
            originator.getStateFromMemento(careTaker.get(stateNum + 1));
            stateNum++;
//            System.out.println("In statehandler redo " + originator.getState());
            System.out.println("Statenum: " + stateNum);
            this.close = checkClose();
            return originator.getState();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Can't redo further");
        }
        return null;
    }

    // checks to see if a shape was drawn after undo/redo
    // This results in a new state path which discards all state after the current state
    private void checkNewPath() {
        if(stateNum + 1 != careTaker.getMementoList().size()) {
            careTaker.resetMementoList(stateNum);
        }
    }

    private boolean checkClose() {
        return stateNum != 0;
    }

    public boolean getClose() {
        return close;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        stringBuilder.append("Statenum is: " + stateNum + "\n");
        for(Memento m : careTaker.getMementoList()) {
            stringBuilder.append(index);
            stringBuilder.append(" ");
            stringBuilder.append(m.toString());
            stringBuilder.append("\n");
            index++;
        }
        return stringBuilder.toString();
    }
}