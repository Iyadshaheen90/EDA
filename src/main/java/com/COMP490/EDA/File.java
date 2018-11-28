package com.COMP490.EDA;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class File {
    private Pane pane;
    private double lineStartX;
    private double lineStartY;
    private double lineEndX;
    private double lineEndY;
    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;
    private TreeView tree;

    public File(Pane pane, int width, int height, TreeView tree){
        this.pane = pane;
        //Draw background rows
        for(int i=0; i<= height ; i=i+20){
            Line l = new Line();
            l.setStartX(0);
            l.setEndX(width);
            l.setStartY(i);
            l.setEndY(i);
            l.setStrokeWidth(0.2);
            pane.getChildren().add(l);

        }
        //draw background columns
        for(int i=0; i<= width ; i=i+20){
            Line l = new Line();
            l.setStartX(i);
            l.setEndX(i);
            l.setStartY(0);
            l.setEndY(height);
            l.setStrokeWidth(0.2);
            pane.getChildren().add(l);

        }
        //Develop TreeView of objects
        //TreeView treeView = new TreeView();
        TreeItem rootItem = new TreeItem("Root");
        TreeItem test = new TreeItem("test");
        rootItem.getChildren().addAll(
                test
        );

        //treeView.setRoot(rootItem);
        tree.setRoot(rootItem);


    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    // Might change depending on how we want to use
    public Shape getShape(Shape shape) {
        return shapes.get(shapes.indexOf(shape));
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    //Drawing a line (wire) will have one click at start location and one click at ending location. Call addLineStart on
    //first click, call addLineEnd on last click to designate line.

    public void addLineEnd(double x, double y){
        lineEndX=x;
        lineEndY=y;
        Line l = new Line();
        l.setStartX(lineStartX);
        l.setStartY(lineStartY);
        l.setEndX(lineEndX);
        l.setEndY(lineEndY);
        l.setStrokeWidth(2);
        pane.getChildren().add(l);
    }
    public void addRectangle(double x, double y){
        Rectangle rect = new Rectangle();
        //Change these when fully implemented
        rect.setHeight(25);
        rect.setWidth(25);
        /////////////////////////////////////
        rect.setX(x);
        rect.setY(y);
        rect.setStrokeWidth(2);
        shapes.add(rect);
        pane.getChildren().add(rect);
        TreeItem item = new TreeItem("rectangle");
        tree.getRoot().getChildren().addAll(item);
    }
    public void addCircle(double x, double y){
        Circle c = new Circle();
        c.setCenterX(x);
        c.setCenterY(y);
        //Change this when fully implemented
        c.setRadius(2);
        ////////////////////////////////////
        c.setStrokeWidth(5);
        shapes.add(c);
        pane.getChildren().add(c);
        TreeItem item = new TreeItem("circle");
        tree.getRoot().getChildren().addAll(item);
    }


}
