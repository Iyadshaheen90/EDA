package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Drawable {
    private Pane drawArea;
    private String tool;
    private ArrayList<Shape> shapes;
    private double startX;
    private double startY;
    private double orgSceneX;
    private double orgSceneY;
    private double orgTranslateX;
    private double orgTranslateY;
    private boolean draggable;


    public Drawable(Pane drawArea, String tool, ArrayList<Shape> shapes) {
        this.drawArea = drawArea;
        this.tool = tool;
        this.shapes = shapes;
    }

    public void setStartPoint(double x, double y) {
        System.out.println("STart point set to X: " + x + " Y: " + y);
        startX = x;
        startY = y;
    }

    public void updateTool(String tool){
        this.tool = tool;
    }

    private double distance(double x, double y) {
        double xDistance = Math.pow((x - startX), 2);
        double yDistance = Math.pow((y - startY), 2);
        return Math.sqrt(xDistance + yDistance);
    }


    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {
                    if(tool=="move") {
                        orgSceneX = me.getSceneX();
                        orgSceneY = me.getSceneY();
                        orgTranslateX = ((Circle) (me.getSource())).getTranslateX();
                        orgTranslateY = ((Circle) (me.getSource())).getTranslateY();
                        draggable = true;
                    }
                    else
                        draggable =false;
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {
                    if(draggable) {

                        double offsetX = me.getSceneX() - orgSceneX;
                        double offsetY = me.getSceneY() - orgSceneY;
                        double newTranslateX = orgTranslateX + offsetX;
                        double newTranslateY = orgTranslateY + offsetY;

                        ((Circle) (me.getSource())).setTranslateX(newTranslateX);
                        ((Circle) (me.getSource())).setTranslateY(newTranslateY);
                    }
                }
            };

    EventHandler<MouseEvent> rectOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {
                    if(tool=="move") {
                        orgSceneX = me.getSceneX();
                        orgSceneY = me.getSceneY();
                        orgTranslateX = ((Rectangle) (me.getSource())).getTranslateX();
                        orgTranslateY = ((Rectangle) (me.getSource())).getTranslateY();
                        draggable = true;
                    }
                    else
                        draggable =false;
                }
            };

    EventHandler<MouseEvent> rectOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {
                    if(draggable) {

                        double offsetX = me.getSceneX() - orgSceneX;
                        double offsetY = me.getSceneY() - orgSceneY;
                        double newTranslateX = orgTranslateX + offsetX;
                        double newTranslateY = orgTranslateY + offsetY;

                        ((Rectangle) (me.getSource())).setTranslateX(newTranslateX);
                        ((Rectangle) (me.getSource())).setTranslateY(newTranslateY);
                    }
                }
            };

        EventHandler<MouseEvent> lineOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {
                    if(tool=="move") {
                        orgSceneX = me.getSceneX();
                        orgSceneY = me.getSceneY();
                        orgTranslateX = ((Line) (me.getSource())).getTranslateX();
                        orgTranslateY = ((Line) (me.getSource())).getTranslateY();
                        draggable = true;
                    }
                    else
                        draggable =false;
                }
            };

    EventHandler<MouseEvent> lineOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {
                    if(draggable) {

                        double offsetX = me.getSceneX() - orgSceneX;
                        double offsetY = me.getSceneY() - orgSceneY;
                        double newTranslateX = orgTranslateX + offsetX;
                        double newTranslateY = orgTranslateY + offsetY;

                        ((Line) (me.getSource())).setTranslateX(newTranslateX);
                        ((Line) (me.getSource())).setTranslateY(newTranslateY);
                    }
                }
            };


    public void drawShape(double x, double y, Color color) {
        switch (tool) {
            case "select":
                break;
            case "line":
                Line line = new Line(startX, startY, x, y);
                line.setStroke(color);
                System.out.println("color: "+color);
                System.out.println("line color: "+line.getFill());
                line.setOnMouseClicked(lineOnMousePressedEventHandler);
                line.setOnMouseDragged(lineOnMouseDraggedEventHandler);
                shapes.add(line);
                drawArea.getChildren().add(line);
                break;
            case "rectangle":
                double width = x - startX;
                double height = y - startY;
                // If end point is less than start swap points and make width/height positive
                if(width < 0) {
                    startX = x;
                    width = Math.abs(width);
                }
                if(height < 0) {
                    startY = y;
                    height = Math.abs(height);
                }
                System.out.println(startX + " " + startY);
                Rectangle rect = new Rectangle(startX, startY, width, height);
                rect.setFill(color);
                rect.setOnMouseClicked(rectOnMousePressedEventHandler);
                rect.setOnMouseDragged(rectOnMouseDraggedEventHandler);
                shapes.add(rect);
                drawArea.getChildren().add(rect);
                break;
            case "circle":
                Circle circle = new Circle();
                circle.setCenterX(startX);
                circle.setCenterY(startY);
                circle.setRadius(distance(x, y)/2);
                circle.setFill(color);
                circle.setOnMouseClicked(circleOnMousePressedEventHandler);
                circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
                shapes.add(circle);
                drawArea.getChildren().add(circle);
                break;
        }



        System.out.println(tool + " end point set to X: " + x + " Y: " + y);
        TreeItem item = new TreeItem(tool);
//        tree.getRoot().getChildren().addAll(item);
    }
}
