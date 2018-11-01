package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewController {
    private Canvas canvas;

    @FXML
    private TextField width;
    @FXML
    private TextField height;

    public NewController(Canvas canvas) {
        this.canvas = canvas;
    }

    public void initialize() {
        // force the field to be numeric only
        width.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!width.getText().matches("\\d*")) {
                    width.setText(width.getText().replaceAll("[^\\d]", ""));
                }
            }
        });
        // force the field to be numeric only
        height.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!height.getText().matches("\\d*")) {
                    height.setText(height.getText().replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    // Add canvas listeners
    public void addCanvasListeners() {
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
                //mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY());
            }
        });
    }

    private void drawShape(double x, double y, double w, double h) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillOval(x, y, w, h);
    }

    @FXML
    public void handleOK() {
        if(!width.getText().equals("") && !height.getText().equals("")) {
            canvas = new Canvas(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
            //mainArea.setContent(canvas);
            //addCanvasListeners();
            Stage stage = (Stage) width.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public  void handleCancel() {
        Stage stage = (Stage) width.getScene().getWindow();
        stage.close();
    }
}
