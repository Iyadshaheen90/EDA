package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Symbol {

    private String name;
    private int width;
    private int height;
    private double initialX;
    private double initialY;
    private Pane drawArea;
    private ToolBarController toolBar;
    private boolean clicked = false;
    private String parentDir="";
    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;
    private Drawable draw;

    public Symbol(){
        //Empty Symbol
    }
    public Symbol(Pane drawArea, int width, int height , ToolBarController toolBar, Accordion sidePanel) {
        this.parentDir=Global.getLibraryLoc();
        this.drawArea = drawArea;
        this.width = width;
        this.height = height;
        this.toolBar = toolBar;
        shapes = new ArrayList<>();
        draw = new Drawable(drawArea, toolBar.getTool(), shapes);
        initialize();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void initialize() {
        drawBackground();
        addMouseScrolling(drawArea);
        addDragListeners(drawArea);
        addDrawListeners();
    }


    // draw function for the background
    private void drawBackground() {
        //Draw background rows
        for(int i=0; i<= height ; i=i+20){
            Line l = new Line();
            l.setStartX(0);
            l.setEndX(width);
            l.setStartY(i);
            l.setEndY(i);
            l.setStrokeWidth(0.2);
            drawArea.getChildren().add(l);
        }
        //draw background columns
        for(int i=0; i<= width ; i=i+20){
            Line l = new Line();
            l.setStartX(i);
            l.setEndX(i);
            l.setStartY(0);
            l.setEndY(height);
            l.setStrokeWidth(0.2);
            drawArea.getChildren().add(l);
        }
    }

    // function for scrolling and scaling of the pane
    public void addMouseScrolling(Node node) {
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

    // function to allow dragging in the editable area
    private void addDragListeners(final Node n) {

        n.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(me.getButton()!=MouseButton.MIDDLE && me.isControlDown()
                        && toolBar.getTool()=="select")
                {
                    initialX = me.getX();
                    initialY = me.getY();
                }

                //shapes dragging
                else if(me.getButton()!=MouseButton.MIDDLE && toolBar.getTool()=="move")
                {
                    System.out.println("works");
                    initialX = me.getX();
                    initialY = me.getY();
                }
            }
        });

        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me)
            {
                if(me.getButton()!=MouseButton.MIDDLE && me.isControlDown()
                        && toolBar.getTool()=="select")
                {
                    n.setTranslateX(me.getX() + n.getTranslateX() - initialX);
                    n.setTranslateY(me.getY() + n.getTranslateY() - initialY);
                }

                //shapes dragging
                else if(me.getButton()!=MouseButton.MIDDLE && toolBar.getTool()=="move")
                {
                    drawArea.getChildren().get(52).setTranslateX(me.getX() + n.getTranslateX() - initialX);
                    drawArea.getChildren().get(52).setTranslateY(me.getY() + n.getTranslateY() - initialY);
                }
            }
        });
    }

    public void addDrawListeners() {
        drawArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draw.updateTool(toolBar.getTool());
                if(clicked) {
                    // add shape
                    draw.drawShape(event.getX(), event.getY(), toolBar.getColor());
                    //remove onMouseMove handler
                    clicked = false;
                }

                else {
                    //set shape start point
                    draw.setStartPoint(event.getX(), event.getY());
                    //add onMouseMove handler
//                    drawArea.setOnMouseMoved(new EventHandler<MouseEvent>() {
//                        @Override
//                        public void handle(MouseEvent event) {
//                            drawShape(event.getX(), event.getY());
//                        }
//                    });
                    clicked = true;
                }
            }
        });
    }

    // Shape arraylist controls
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    // Might change depending on how we want to use
    public Shape getShape(Shape shape) {
        return shapes.get(shapes.indexOf(shape));
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }
}
