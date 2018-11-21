package com.COMP490.EDA;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;

public class file {
    private Pane pane;
    public file(Pane pane){
        this.pane = pane;
        int rownum= (int) pane.getPrefHeight();
        for(int i=0; i< rownum ; i=i+5){
            Line l = new Line();
            l.setStartX(0);
            l.setEndX(rownum);
            l.setStartY(i);
            l.setEndY(i);
            pane.getChildren().add(l);

        }
    }

}
