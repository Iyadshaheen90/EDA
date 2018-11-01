package com.COMP490.EDA;

import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcType;

public class Controller {
    @FXML
    private Label mouseCoordinates;
    @FXML
    private ScrollPane mainArea;

    private Canvas canvas = new Canvas(500, 500);

    public void initialize() {
        addListeners();
        mainArea.setContent(canvas);
    }

    private void drawShape(double x, double y, double w, double h) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillOval(x, y, w, h);
    }

    // Add all listener initializer
    public void addListeners() {
        addBottomRightCoordinateListener();
        addClickListener();
    }

    private void addClickListener() {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                drawShape(event.getX(), event.getY(), 30, 30);
            }
        });
    }

    // Set up listener for coordinates at the bottom right
    public void addBottomRightCoordinateListener() {
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY());
            }
        });
    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
