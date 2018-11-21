package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
public class NewController {
    @FXML
    private TextField width;
    @FXML
    private TextField height;

    private TabPane tabArea;
    private Label mouseCoordinates;
    private file [] files = new file[10];

    public NewController(TabPane tabArea, Label mouseCoordinates) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
    }

    public void initialize() {
        // force the field to be numeric only
        width.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!width.getText().matches("\\d*")) {
                    //width.setText(width.getText().replaceAll("[^\\d]", ""));
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
    private void addPaneListeners(Pane pane) {
        // Coordinate listener
        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY());
            }
        });
        // Draw listener
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                drawShape(event.getX(), event.getY(), 30, 30);
            }
        });
    }

    private void drawShape(double x, double y, double w, double h) {
        System.out.println(x + "  " + y);
        //pane.getChildren().addAll(new Rectangle(x, y, 10, 10));
    }

    @FXML
    public void handleOK() {
        if(!width.getText().equals("") && !height.getText().equals("")) {
            Pane pane = new Pane();
            pane.setMaxSize(Double.parseDouble(width.getText()), Double.parseDouble(height.getText()));
            pane.setStyle("-fx-background-color: white");
            Tab tab = new Tab("New Tab" , pane);
            file f=new file(pane);
            files[0]=f;
            tabArea.getTabs().add(tab);
            addPaneListeners(pane);
            Stage stage = (Stage) width.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void handleCancel() {
        Stage stage = (Stage) width.getScene().getWindow();
        stage.close();
    }
}
