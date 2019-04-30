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
    private ArrayList<Shape> shapes;
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
  
    //moved shapes holder
    private Line movedLine;
    private Rectangle movedRectangle;
    private Circle movedCircle;

    public Drawable(Pane drawArea, String tool, ArrayList<Shape> shapes) {
        this.drawArea = drawArea;
        this.tool = tool;
        this.shapes = shapes;
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

//                    if(tool=="select")
//                    {
//                        System.out.println("recognized");
//                        sidePanel.requestFocus();
//                        sidePanel.getPanes().get(1).getContent().setVisible(false);
//
//                        draggable = false;
//                    }
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

                        ((Circle) (me.getSource())).setTranslateX(newTranslateX);
                        ((Circle) (me.getSource())).setTranslateY(newTranslateY);
                    }
                }
            };

    private EventHandler<MouseEvent> circleOnMouseDraggedReleasedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("drag over");
//                    movedCircle.setCenterX(o);
//                    draggable =false;
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
                        ((Rectangle) (me.getSource())).setTranslateX(newTranslateX);
                        ((Rectangle) (me.getSource())).setTranslateY(newTranslateY);
                    }
                }
            };

    private EventHandler<MouseEvent> rectOnMouseDraggedReleasedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("drag over");
//                    movedCircle.setCenterX(o);
//                    draggable =false;
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
                        double newTranslateX = origTranslateX + offsetX;
                        double newTranslateY = origTranslateY + offsetY;

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
                drawArea.getChildren().remove(line);
                Line line = new Line(startX, startY, x, y);
                line.setStartX(startX);
                line.setStroke(color);
                System.out.println("color: "+color);
                System.out.println("line color: "+line.getFill());
                line.setOnMouseClicked(lineOnMousePressedEventHandler);
                line.setOnMouseDragged(lineOnMouseDraggedEventHandler);
                line.setId("Line");
                shapes.add(line);
                drawArea.getChildren().add(line);
                Global.getCurrentStateHandler().save(drawArea);

                break;
            case "rectangle":
                //remove the preview rectangle when the second click of the mouse happens and then draw the actual line
                drawArea.getChildren().remove(rectangle);
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
                Rectangle rect = new Rectangle(startX, startY, width, height);
                rect.setFill(color);
                rect.setOnMouseClicked(rectOnMousePressedEventHandler);
                rect.setOnMouseDragged(rectOnMouseDraggedEventHandler);
                rect.setId("Rectangle");
                shapes.add(rect);
                drawArea.getChildren().add(rect);
                Global.getCurrentStateHandler().save(drawArea);

                break;
            case "circle":
                //remove the preview circle when the second click of the mouse happens and then draw the actual line
                drawArea.getChildren().remove(circle);
                Circle circle = new Circle();
                circle.setCenterX(Math.abs(startX+x)/2);
                circle.setCenterY(Math.abs(startY+y)/2);
                circle.setRadius(distance(x, y)/2);
                circle.setFill(color);
                circle.setOnMouseClicked(circleOnMousePressedEventHandler);
                circle.setOnMouseDragged(circleOnMouseDraggedEventHandler);
//                circle.setOnMouseReleased(circleOnMouseDraggedReleasedEventHandler);
                circle.setId("Circle");
//                circle.setStroke(Paint.valueOf("0"));
                shapes.add(circle);
                drawArea.getChildren().add(circle);
                Global.getCurrentStateHandler().save(drawArea);
                break;
        }

        System.out.println(tool + " end point set to X: " + x + " Y: " + y);
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
