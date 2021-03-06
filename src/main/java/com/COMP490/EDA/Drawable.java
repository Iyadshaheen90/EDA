package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Drawable {
    private Pane drawArea;
    private String tool;
    private double startX;
    private double startY;
    private double origSceneY;
    private double origSceneX;
    private double origTranslateX;
    private double origTranslateY;

    private boolean draggable;

    //the shapes
    private Line line;
    private Rectangle rectangle;
    private Circle circle;
    private double OFFSETX;
    private double OFFSETY;
    //moved shapes holder
    private Line movedLine;
    private Rectangle movedRectangle;
    private Circle movedCircle;
    private Magnetize mag;

    public Drawable(Pane drawArea, String tool) {
        this.drawArea = drawArea;
        this.tool = tool;
        this.mag = new Magnetize();
    }
  
    public Drawable(){

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


    private EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if (tool.equals("move")) {
                        origSceneX = me.getSceneX();
                        origSceneY = me.getSceneY();
                        origTranslateX = ((Circle) (me.getSource())).getTranslateX();
                        origTranslateY = ((Circle) (me.getSource())).getTranslateY();
                        draggable = true;
                    }

                    else
                        draggable = false;
                }
            };

    private EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if(draggable && tool.equals("move")) {
                        double offsetX = me.getSceneX() - origSceneX;
                        double offsetY = me.getSceneY() - origSceneY;
                        double newTranslateX = origTranslateX + offsetX;
                        double newTranslateY = origTranslateY + offsetY;

                        ((Circle) (me.getSource())).setTranslateX(20*Math.round(newTranslateX/20));
                        ((Circle) (me.getSource())).setTranslateY(20*Math.round(newTranslateY/20));
                    }
                }
            };


    private EventHandler<MouseEvent> rectOnMousePressedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if (tool.equals("move")) {
                        origSceneX = me.getSceneX();
                        origSceneY = me.getSceneY();
                        origTranslateX = ((Rectangle) (me.getSource())).getTranslateX();
                        origTranslateY = ((Rectangle) (me.getSource())).getTranslateY();
                        draggable = true;
                    } else
                        draggable = false;
                }
            };

    private EventHandler<MouseEvent> rectOnMouseDraggedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if (draggable) {
                        double offsetX = me.getSceneX() - origSceneX;
                        double offsetY = me.getSceneY() - origSceneY;
                        double newTranslateX = origTranslateX + offsetX;
                        double newTranslateY = origTranslateY + offsetY;
                        ((Rectangle) (me.getSource())).setTranslateX(20*Math.round(newTranslateX/20));
                        ((Rectangle) (me.getSource())).setTranslateY(20*Math.round(newTranslateY/20));

                    }
                }
            };

    private EventHandler<MouseEvent> lineOnMousePressedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if (tool.equals("move")) {
                        origSceneX = me.getSceneX();
                        origSceneY = me.getSceneY();
                        origTranslateX = ((Line) (me.getSource())).getTranslateX();
                        origTranslateY = ((Line) (me.getSource())).getTranslateY();
                        draggable = true;
                    } 
                }
            };

    private EventHandler<MouseEvent> lineOnMouseDraggedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if(draggable && tool.equals("move")) {
                        double offsetX = me.getSceneX() - origSceneX;
                        double offsetY = me.getSceneY() - origSceneY;
                        OFFSETX = offsetX;
                        OFFSETY = offsetY;
                        double newTranslateX = origTranslateX + offsetX;
                        double newTranslateY = origTranslateY + offsetY;

                        ((Line) (me.getSource())).setTranslateX(20*Math.round(newTranslateX/20));
                        ((Line) (me.getSource())).setTranslateY(20*Math.round(newTranslateY/20));
                    }
                }
            };

    private EventHandler<MouseEvent> lineOnMouseReleasedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent me) {
                    if(draggable && tool.equals("move")) {
                        line.setStartX(OFFSETX + line.getTranslateX());
                        line.setStartY(OFFSETY + line.getTranslateY());
                        line.setEndX(OFFSETX + line.getTranslateX());
                        line.setEndY(OFFSETY + line.getTranslateY());
                        line.setTranslateX(0);
                        line.setTranslateY(0);
                        System.out.println("Mouse released " + line.getStartX() + " " + line.getStartY());
                    }
                }
            };

    public void drawShape(double x, double y, Color color) {
        switch (tool) {
            case "select":
                break;
            case "line":
                //remove the preview line when the second click of the mouse happens and then draw the actual line
                Line line = this.line;
                System.out.println("color: "+color);
                System.out.println("line color: "+line.getFill());
                line.setOnMouseClicked(lineOnMousePressedEventHandler);
                line.setOnMouseDragged(lineOnMouseDraggedEventHandler);
                line.setOnMouseDragReleased(lineOnMouseReleasedEventHandler);  // Not executing ofr some reason
                line.setId("Line");
                drawArea.getChildren().remove(this.line);
                Global.getCurrentSymbol().getShapes().add(line);
                Global.getCurrentStateHandler().save(Global.getCurrentSymbol().getShapes());
                drawArea.getChildren().add(line);

                break;
            case "rectangle":
                //remove the preview rectangle when the second click of the mouse happens and then draw the actual line
                drawArea.getChildren().remove(this.rectangle);
                Rectangle rect = this.rectangle;
                rect.setOnMouseClicked(rectOnMousePressedEventHandler);
                rect.setOnMouseDragged(rectOnMouseDraggedEventHandler);
                rect.setId("Rectangle");
                drawArea.getChildren().remove(this.rectangle);
                Global.getCurrentSymbol().getShapes().add(rect);
                Global.getCurrentStateHandler().save(Global.getCurrentSymbol().getShapes());
                drawArea.getChildren().add(rect);

                break;
            case "circle":
                //remove the preview circle when the second click of the mouse happens and then draw the actual line
                Circle circle = this.circle;
                circle.setOnMouseClicked(circleOnMousePressedEventHandler);
                circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
                circle.setId("Circle");
                drawArea.getChildren().remove(this.circle);
                Global.getCurrentSymbol().getShapes().add(circle);
                Global.getCurrentStateHandler().save(Global.getCurrentSymbol().getShapes());
                drawArea.getChildren().add(circle);
                break;
        }

        System.out.println(tool + " end point set to X: " + x + " Y: " + y);
        System.out.println("Shapes is now: " + Global.getCurrentSymbol().getShapes());
        System.out.println("Statelist for: " + Global.getCurrentSymbol().getName() + "\n"+
                Global.getCurrentStateHandler());
    }
    public void addExist(Shape s){
        if (s instanceof Line){
            System.out.println("Hello");
            s.setOnMouseClicked(lineOnMousePressedEventHandler);
            s.setOnMouseDragged(lineOnMouseDraggedEventHandler);
        }
        else if (s instanceof Rectangle){
            System.out.println("Hello");

            s.setOnMouseClicked(rectOnMousePressedEventHandler);
            s.setOnMouseDragged(rectOnMouseDraggedEventHandler);
        }
        else{
            System.out.println("Hello");

            s.setOnMouseClicked(circleOnMousePressedEventHandler);
            s.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        }
    }
    public void shapePreview(MouseEvent me, Color color) {
        switch (tool)
        {
            case "line":
                line.setStroke(color);
                line.setStartX(startX);
                line.setEndX(20*(Math.round(me.getX()/20)));
                line.setStartY(startY);
                line.setEndY(20*(Math.round(me.getY()/20)));
                break;

            case "rectangle":
                rectangle.setFill(color);
                double width = 20*(Math.round(me.getX()/20)) - startX;
                double height = 20*(Math.round(me.getY()/20)) - startY;

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
//                if(drawArea.getLayoutBounds().contains(circle.getBoundsInParent())) {
                    circle.setFill(color);
                    double centerX = 20 * Math.round((Math.abs(startX + me.getX()) / 2) / 20);
                    circle.setCenterX(centerX);
                    double centerY = 20 * Math.round((Math.abs(startY + me.getY()) / 2) / 20);
                    circle.setCenterY(centerY);
                    double distance = 20 * Math.round((distance(me.getX(), me.getY()) / 2) / 20);
                    circle.setRadius(distance);
                    System.out.println(centerY);
//                }
                break;
        }
    }
    //a function to delete the shape being drawn while user is drawing it when the user hits escape
    public void exitDrawing() {
        System.out.println("exit drawing");
        switch (tool){
            case "line":
                drawArea.getChildren().remove(line);
                break;
            case "rectangle":
                drawArea.getChildren().remove(rectangle);
                break;

            case "circle":
                drawArea.getChildren().remove(circle);
                break;
        }
    }
}
