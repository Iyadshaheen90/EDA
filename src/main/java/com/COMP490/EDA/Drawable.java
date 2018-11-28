package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Drawable {
    private File file;
    private Pane drawArea;
    private String tool;
    private double startX;
    private double startY;
    private boolean clicked;

    public Drawable(File file, Pane drawArea, String tool) {
        this.file = file;
        this.drawArea = drawArea;
        this.tool = tool;
        this.clicked = false;
    }

    public void addListeners() {
        drawArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(clicked) {
                    //remove onMouseMove handler
                    //set shape end point
                    //file.
                    clicked = false;
                }

                else {
                    //add onMouseMove handler
                    //set shape start point
                    startX = event.getX();
                    startY = event.getY();
                    System.out.println("Start drawing at X: " + startX + " Y: " + startY);
                    drawArea.setOnMouseMoved(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            drawShape(event.getX(), event.getY());
                        }
                    });
                    clicked = !clicked;
                }
            }
        });
    }

    private void drawShape(double x, double y) {
        switch (tool) {
            case "select":
                break;
            case "line":
                Line line = new Line(startX, startY, x, y);
                drawArea.getChildren().add(line);
                break;
            case "rectangle":
                break;
            case "circle":
                break;
        }
    }
}
