package com.COMP490.EDA;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Magnetize {
    private ArrayList<Shape> shapes;
    public Magnetize(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public ArrayList<Double> getEdges() {
        ArrayList<Double> edges = new ArrayList<>();
        getEdgesX(edges);
        getEdgesY(edges);
        return edges;
    }

    private void getEdgesX(ArrayList<Double> edges) {
        for(Shape s : shapes) {
            if(s instanceof Line) {
                // since start and end X are the same it is straight line on X axis
                if(((Line) s).getStartX() == ((Line) s).getEndX()) {
                    edges.add(((Line) s).getStartX());
                }
            }
            // add each of the X edges
            else if (s instanceof Rectangle) {
                edges.add(((Rectangle) s).getX());
                edges.add(((Rectangle) s).getX() + ((Rectangle) s).getWidth());
            }

            else if(s instanceof Circle) {
                edges.add(((Circle) s).getCenterX() + ((Circle) s).getRadius());
                edges.add(((Circle) s).getCenterX() - ((Circle) s).getRadius());
            }
            else {
                System.out.println("Not a symbol");
            }
        }
    }

    private void getEdgesY(ArrayList<Double> edges) {
        for(Shape s : shapes) {
            if(s instanceof Line) {
                // since start and end Y are the same it is straight line on Y axis
                if(((Line) s).getStartY() == ((Line) s).getEndY()) {
                    edges.add(((Line) s).getStartY());
                }
            }
            // add each of the Y edges
            else if (s instanceof Rectangle) {
                edges.add(((Rectangle) s).getY());
                edges.add(((Rectangle) s).getY() + ((Rectangle) s).getWidth());
            }

            else if(s instanceof Circle) {
                edges.add(((Circle) s).getCenterY() + ((Circle) s).getRadius());
                edges.add(((Circle) s).getCenterY() - ((Circle) s).getRadius());
            }
            else {
                System.out.println("Not a symbol");
            }
        }
    }
}
