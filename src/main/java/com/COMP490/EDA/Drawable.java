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
    //the shapes
    private Line line;
    private Rectangle rectangle;
    private Circle circle;

    public Drawable(Pane drawArea, String tool, ArrayList<Shape> shapes) {
        this.drawArea = drawArea;
        this.tool = tool;
        this.shapes = shapes;
    }

    public void setStartPoint(double x, double y) {
        System.out.println("STart point set to X: " + x + " Y: " + y);
        startX = x;
        startY = y;
        switch (tool)
        {
            case "line":
                line = new Line(startX,startY,startX,startY);
                drawArea.getChildren().add(line);
                break;
                
            case "rectangle":
                rectangle = new Rectangle();
                drawArea.getChildren().add(rectangle);
                break;

            case "circle":
                circle = new Circle();
                circle.setCenterX(Math.abs(startX+x)/2);
                circle.setCenterY(Math.abs(startY+y)/2);
                circle.setRadius(distance(x, y)/2);
                drawArea.getChildren().add(circle);
                break;
        }
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
                //remove the preview line when the second click of the mouse happens and then draw the actual line
                drawArea.getChildren().remove(drawArea.getChildren().indexOf(line));
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
                //remove the preview rectangle when the second click of the mouse happens and then draw the actual line
                drawArea.getChildren().remove(drawArea.getChildren().indexOf(rectangle));
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
                //remove the preview circle when the second click of the mouse happens and then draw the actual line
                drawArea.getChildren().remove(drawArea.getChildren().indexOf(circle));
                Circle circle = new Circle();
                circle.setCenterX(Math.abs(startX+x)/2);
                circle.setCenterY(Math.abs(startY+y)/2);
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

    public void shapePreview(MouseEvent me, Color color) {
        //System.out.println("accessed");
        switch (tool)
        {
            case "line":
                line.setStroke(color);
                line.setStartX(startX);
                line.setEndX(me.getX());
                line.setStartY(startY);
                line.setEndY(me.getY());
                break;

            case "rectangle":
                rectangle.setFill(color);
                double width = me.getX() - startX;
                double height = me.getY() - startY;

                if(width<0)
                {
                    rectangle.setTranslateX(width);
                }
                if(height<0)
                {
                    rectangle.setTranslateY(height);
                }

                rectangle.setX(startX);
                rectangle.setY(startY);
                rectangle.setWidth(Math.abs(width));
                rectangle.setHeight(Math.abs(height));
                break;

            case "circle":
                circle.setFill(color);
                circle.setCenterX(Math.abs(startX+me.getX())/2);
                circle.setCenterY(Math.abs(startY+me.getY())/2);
                circle.setRadius(distance(me.getX(), me.getY())/2);
                break;
        }
    }

    public void exitDrawing() {
        System.out.println("exit drawing");
        switch (tool){
            case "line":
                drawArea.getChildren().remove(drawArea.getChildren().indexOf(line));
                break;
            case "rectangle":
                drawArea.getChildren().remove(drawArea.getChildren().indexOf(rectangle));
                break;

            case "circle":
                drawArea.getChildren().remove(drawArea.getChildren().indexOf(circle));
                break;
        }
    }
}
