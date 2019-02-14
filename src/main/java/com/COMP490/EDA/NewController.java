package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NewController {
    @FXML
    private TextField width;
    @FXML
    private TextField height;

    private TabPane tabArea;
    private Label mouseCoordinates;
    private TreeView tree;
    private Accordion sidePanel;
    private ToolBarController toolBar;

    public NewController(TabPane tabArea, Label mouseCoordinates, ToolBarController toolBar, Accordion sidePanel) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.toolBar = toolBar;
        this.sidePanel = sidePanel;
        this.tree = (TreeView) sidePanel.getPanes().get(2).getContent();
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

    // Add coordinate listeners
    private void addCoordinateListener(Project file, Pane pane) {
        // Coordinate listener
        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY());
            }
        });
    }

    @FXML
    public void handleOK() {
        if(!width.getText().equals("") && !height.getText().equals("")) {
            Pane pane = new Pane();
            pane.setMaxSize(Double.parseDouble(width.getText()), Double.parseDouble(height.getText()));
            pane.setStyle("-fx-background-color: white");
            Tab tab = new Tab("New Tab" , pane);
            Project project = new Project(pane, Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), toolBar,  sidePanel);
//            Global.addToArrayList(project);
            tabArea.getTabs().add(tab);
            addCoordinateListener(project, pane);
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
