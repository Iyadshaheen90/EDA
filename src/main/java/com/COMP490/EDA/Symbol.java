package com.COMP490.EDA;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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
    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;
    private Drawable draw;

    public Symbol(){
        //Empty Symbol
    }
    public Symbol(String name, Pane drawArea, int width, int height , ToolBarController toolBar) {
        this.name = name;
        this.drawArea = drawArea;
        this.width = width;
        this.height = height;
        this.toolBar = toolBar;
        shapes = new ArrayList<>();
        draw = new Drawable(drawArea, toolBar.getTool(), shapes);
        initialize();
    }

    public void setDrawArea(ArrayList<Node> children) {
        this.drawArea.getChildren().removeAll(this.drawArea.getChildren());
        this.drawArea.getChildren().addAll(children);
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

    public void setShapes(ArrayList<Shape> shapes){
        this.shapes=shapes;
    }

    public Pane getDrawArea(){ return this.drawArea; }

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


    // draw function for the background grid
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
        n.setOnMousePressed(me -> {
            if(me.getButton()!=MouseButton.MIDDLE && me.isControlDown()
                    && toolBar.getTool().equals("select"))
            {
                initialX = me.getX();
                initialY = me.getY();
            }

        });

        n.setOnMouseDragged(me -> {
            if(me.getButton()!=MouseButton.MIDDLE && me.isControlDown()
                    && toolBar.getTool().equals("select"))
            {
                n.setTranslateX(me.getX() + n.getTranslateX() - initialX);
                n.setTranslateY(me.getY() + n.getTranslateY() - initialY);
            }
        });
    }

    private void addDrawListeners() {
        drawArea.setOnMouseClicked(event -> {
            drawArea.requestFocus();//focusing on draw area so if the user hits escape it will operate
//                System.out.println(shapes.size());//testing if the shape is getting deleted when escape is pressed
            draw.updateTool(toolBar.getTool());
            if(clicked) {
                // add shape
                draw.drawShape(event.getX(), event.getY(), toolBar.getColor());
                //remove onMouseMove handler
                clicked = false;
            }

            else if(toolBar.getTool().equals("circle") || toolBar.getTool().equals("line")
                    || toolBar.getTool().equals("rectangle"))
            {
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

            drawArea.setOnMouseMoved(mouseEvent -> {
                if (clicked) {
                    draw.shapePreview(mouseEvent, toolBar.getColor());
                }
            });
        });

        drawArea.setOnKeyPressed(keyEvent -> {
            if(clicked&&keyEvent.getCode()==KeyCode.ESCAPE)
            {
                System.out.println("pressed");
                clicked=false;
                draw.exitDrawing();
            }
        });
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }
}
