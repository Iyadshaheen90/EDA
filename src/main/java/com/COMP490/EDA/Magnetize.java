package com.COMP490.EDA;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Magnetize {
    private final double PIXEL_SENSITIVITY = 2;

    private ArrayList<Double> edgesX = new ArrayList<>();
    private ArrayList<Double> edgesY = new ArrayList<>();

    public Magnetize(Symbol symbol) {
        for(Shape shape : symbol.getShapes()) {
            add(shape);
        }
    }

    public ArrayList<Double> getXValues() {
        return edgesX;
    }

    public ArrayList<Double> getYValues() {
        return edgesY;
    }

    public boolean checkXValue(double d) {
        for(Double edge : edgesX) {
            if(Math.abs(d - edge) <= PIXEL_SENSITIVITY) {
                return true;
            }
        }
        return false;
    }

    public boolean checkYValue(double d) {
        for(Double edge : edgesY) {
            if(Math.abs(d - edge) <= PIXEL_SENSITIVITY) {
                return true;
            }
        }
        return false;
    }

    public void add(Shape s) {
        if(s instanceof Line) {
            handleAddLine(s);
        }

        else if (s instanceof Rectangle) {
            handleAddRectangle(s);
        }

        else if(s instanceof Circle) {
//            System.out.println("Adding circle");
            handleAddCircle(s);
        }
        else {
            System.out.println("Not a symbol");
        }
    }

    public void remove(Shape s) {
        if(s instanceof Line) {
            handleRemoveLine(s);
        }

        else if (s instanceof Rectangle) {
            handleRemoveRectangle(s);
        }

        else if(s instanceof Circle) {
            handleRemoveCircle(s);
        }
        else {
            System.out.println("Not a symbol");
        }
    }

    private void handleAddLine(Shape s) {
        // since start and end X are the same it is straight line on X axis
        if(((Line) s).getStartX() == ((Line) s).getEndX()) {
            edgesX.add(((Line) s).getStartX());
        }
        else if(((Line) s).getStartY() == ((Line) s).getEndY()) {
            edgesY.add(((Line) s).getStartY());
        }
    }

    private void handleAddRectangle(Shape s) {
        // add each of the X edges
        edgesX.add(((Rectangle) s).getX());
        edgesX.add(((Rectangle) s).getX() + ((Rectangle) s).getWidth());
        // add each of the Y edges
        edgesY.add(((Rectangle) s).getY());
        edgesY.add(((Rectangle) s).getY() + ((Rectangle) s).getHeight());
        // add center X and Y
        edgesX.add(((Rectangle) s).getX() + (((Rectangle) s).getWidth()/2));
        edgesY.add(((Rectangle) s).getY() + (((Rectangle) s).getHeight()/2));
    }

    private void handleAddCircle(Shape s) {
        edgesX.add(((Circle) s).getCenterX() + ((Circle) s).getRadius());
        edgesX.add(((Circle) s).getCenterX() - ((Circle) s).getRadius());
        edgesX.add(((Circle) s).getCenterX());
        edgesY.add(((Circle) s).getCenterY() + ((Circle) s).getRadius());
        edgesY.add(((Circle) s).getCenterY() - ((Circle) s).getRadius());
        edgesY.add(((Circle) s).getCenterY());
    }

    private void handleRemoveLine(Shape s) {
        // since start and end X are the same it is straight line on X axis
        if(((Line) s).getStartX() == ((Line) s).getEndX()) {
            edgesX.remove(((Line) s).getStartX());
        }
        else if(((Line) s).getStartY() == ((Line) s).getEndY()) {
            edgesY.remove(((Line) s).getStartY());
        }
    }

    private void handleRemoveRectangle(Shape s) {
        // add each of the X edges
        edgesX.remove(((Rectangle) s).getX());
        edgesX.remove(((Rectangle) s).getX() + ((Rectangle) s).getWidth());
        // add each of the Y edges
        edgesY.remove(((Rectangle) s).getY());
        edgesY.remove(((Rectangle) s).getY() + ((Rectangle) s).getHeight());
        // add center X and Y
        edgesX.remove(((Rectangle) s).getX() + (((Rectangle) s).getWidth()/2));
        edgesY.remove(((Rectangle) s).getY() + (((Rectangle) s).getHeight()/2));
    }

    private void handleRemoveCircle(Shape s) {
        edgesX.remove(((Circle) s).getCenterX() + ((Circle) s).getRadius());
        edgesX.remove(((Circle) s).getCenterX() - ((Circle) s).getRadius());
        edgesX.remove(((Circle) s).getCenterX());
        edgesY.remove(((Circle) s).getCenterY() + ((Circle) s).getRadius());
        edgesY.remove(((Circle) s).getCenterY() - ((Circle) s).getRadius());
        edgesY.remove(((Circle) s).getCenterY());
    }
}
