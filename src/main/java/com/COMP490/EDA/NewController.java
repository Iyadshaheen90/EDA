package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class NewController {
    private StackPane mainArea;
    private Label mouseCoordinates;
    private Pane pane;

    @FXML
    private TextField width;
    @FXML
    private TextField height;

    public NewController(StackPane mainArea, Label mouseCoordinates) {
        this.mainArea = mainArea;
        this.mouseCoordinates = mouseCoordinates;
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
    public void addMainAreaListeners() {
        addBottomRightCoordinateListener();
        addClickListener();
    }

    private void addClickListener() {
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                drawShape(event.getX(), event.getY(), 30, 30);
            }
        });
    }

    // Set up listener for coordinates at the bottom right
    public void addBottomRightCoordinateListener() {
        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY());
            }
        });
    }

    private void drawShape(double x, double y, double w, double h) {
        System.out.println(x + "  " + y);
        pane.getChildren().addAll(new Rectangle(x, y, 10, 10));
    }

    @FXML
    public void handleOK() {
        if(!width.getText().equals("") && !height.getText().equals("")) {
            pane = new Pane();
            pane.setMaxSize(Double.parseDouble(width.getText()), Double.parseDouble(height.getText()));
            pane.setStyle("-fx-background-color: white");
            mainArea.getChildren().add(pane);
            addMainAreaListeners();
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
