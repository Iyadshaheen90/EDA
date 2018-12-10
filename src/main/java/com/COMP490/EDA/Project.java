package com.COMP490.EDA;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class Project {
    private int width;
    private int height;
    private double initialX;
    private double initialY;
    private Pane pane;
    private double lineStartX;
    private double lineStartY;
    private double lineEndX;
    private double lineEndY;
    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;
    private TreeView tree;
    private Accordion sidepanel;

    public Project(Pane pane, int width, int height ,Accordion sidepanel){
        this.pane = pane;
        this.width = width;
        this.height = height;
        this.sidepanel = sidepanel;
        drawBackground();
        addMouseScrolling(pane);
        addDragListeners(pane);
        this.tree= (TreeView) sidepanel.getPanes().get(2).getContent();

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

    //function for scrolling and scaling of the pane
    public void addMouseScrolling(Node node)
    {
        node.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            System.out.println(deltaY);
            if (deltaY < 0){
                zoomFactor = 2-zoomFactor;
                node.setScaleX(node.getScaleX() * zoomFactor);
                node.setScaleY(node.getScaleY() * zoomFactor);
            }
            if (deltaY > 0){
                node.setScaleX(node.getScaleX() * zoomFactor);
                node.setScaleY(node.getScaleY() * zoomFactor);
            }
        });
    }

    //function to allow dragging in the editable area
    private void addDragListeners(final Node n){
        n.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(me.getButton()!=MouseButton.MIDDLE)
                {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
                else
                {
                    n.getScene().getWindow().centerOnScreen();
                    initialX = n.getScene().getWindow().getX();
                    initialY = n.getScene().getWindow().getY();
                }

            }
        });

        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(me.getButton()!=MouseButton.MIDDLE)
                {
                    n.setTranslateX(me.getScreenX() - initialX);
                    n.setTranslateY(me.getScreenY() - initialY);
                }
            }
        });
    }

    //draw function for the background
    private void drawBackground()
    {
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
