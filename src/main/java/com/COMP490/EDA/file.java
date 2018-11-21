package com.COMP490.EDA;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class file {
    private Pane pane;
    private int width;
    private int height;
    private double lineStartX;
    private double lineStartY;
    private double lineEndX;
    private double lineEndY;

    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;

    public file(Pane pane, int width, int height){
        this.pane = pane;
        this.width = width;
        this.height = height;
        //Draw background rows
        for(int i=0; i<= height ; i=i+20){
            Line l = new Line();
            l.setStartX(0);
            l.setEndX(width);
            l.setStartY(i);
            l.setEndY(i);
            l.setStrokeWidth(0.5);
            pane.getChildren().add(l);

        }
        //draw background columns
        for(int i=0; i<= width ; i=i+20){
            Line l = new Line();
            l.setStartX(i);
            l.setEndX(i);
            l.setStartY(0);
            l.setEndY(height);
            l.setStrokeWidth(0.5);
            pane.getChildren().add(l);

        }
    }
    //Drawing a line (wire) will have one click at start location and one click at ending location. Call addLineStart on
    //first click, call addLineEnd on last click to designate line.
    public void addLineStart(double x, double y){
        lineStartX=x;
        lineStartY=y;
    }
    public void addLineEnd(double x, double y){
        lineEndX=x;
        lineEndY=y;
        Line l = new Line();
        l.setStartX(lineStartX);
        l.setStartY(lineStartY);
        l.setEndX(lineEndX);
        l.setEndY(lineEndY);
        l.setStrokeWidth(5);
        pane.getChildren().add(l);
    }

}
