package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Project {
    private int width;
    private int height;
    private double initialX;
    private double initialY;
    private Pane drawArea;
    private ToolBarController toolBar;
    boolean clicked = false;
    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;
    private Drawable draw;
    private Accordion sidepanel;

    public Project(Pane drawArea, int width, int height , ToolBarController toolBar, Accordion sidePanel) {
        this.drawArea = drawArea;
        this.width = width;
        this.height = height;
        this.toolBar = toolBar;
        this.sidepanel = sidePanel;
        shapes = new ArrayList<>();
        draw = new Drawable(drawArea, toolBar.getTool(), sidePanel);
        initialize();
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

    public void addDrawListeners() {
        drawArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draw.updateTool(toolBar.getTool());
                if(clicked) {
                    // add shape
                    draw.drawShape(event.getX(), event.getY());
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

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }
}
