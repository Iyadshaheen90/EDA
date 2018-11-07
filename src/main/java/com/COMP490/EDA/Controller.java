package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    @FXML
    private VBox root;
    @FXML
    private Label mouseCoordinates;
    @FXML
    private StackPane mainArea;

    public void initialize() {
        // Create menu bar
        FXMLLoader menuBarLoader = new FXMLLoader(getClass().getResource("/MenuBar.fxml"));
        menuBarLoader.setController(new MenuController(mainArea, mouseCoordinates));
        // Create toolbar
        FXMLLoader toolBarLoader = new FXMLLoader(getClass().getResource("/ToolBar.fxml"));
        toolBarLoader.setController(new ToolBarController(mainArea));
        try {
            MenuBar menuBar = menuBarLoader.load();
            ToolBar toolBar = toolBarLoader.load();
            root.getChildren().addAll(menuBar, toolBar);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
