package com.COMP490.EDA;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NewSymbolController {
    @FXML
    private TextField name;
    @FXML
    private TextField width;
    @FXML
    private TextField height;

    private TabPane tabArea;
    private Label mouseCoordinates;
    private ToolBarController toolBar;
    private Accordion sidePanel;

    public NewSymbolController(TabPane tabArea, Label mouseCoordinates, ToolBarController toolBar, Accordion sidePanel) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.toolBar = toolBar;
        this.sidePanel = sidePanel;
    }

    public void initialize() {
        // force the field to be numeric only
        width.setOnKeyTyped(event -> {
            if(!width.getText().matches("\\d*")) {
                width.setText(width.getText().replaceAll("[^\\d]", ""));
            }
        });
        // force the field to be numeric only
        height.setOnKeyTyped(event -> {
            if(!height.getText().matches("\\d*")) {
                height.setText(height.getText().replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    public void handleOK() {
        if(!name.getText().equals("") && !width.getText().equals("") && !height.getText().equals("")) {
            SymbolLoader sl = new SymbolLoader(tabArea, mouseCoordinates, toolBar, sidePanel);
            sl.loadSymbol(name.getText(), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
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
