package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    @FXML
    private VBox root;

    private StackPane canvasArea;
    private Label mouseCoordinates;

    public void initialize() {
        // Create menu bar
        FXMLLoader menuBarLoader = new FXMLLoader(getClass().getResource("/MenuBar.fxml"));
        menuBarLoader.setController(new MenuController(canvasArea, mouseCoordinates));
        // Create toolbar
        FXMLLoader toolBarLoader = new FXMLLoader(getClass().getResource("/ToolBar.fxml"));
        toolBarLoader.setController(new ToolBarController(canvasArea));
        // Create main area
        FXMLLoader mainAreaLoader = new FXMLLoader(getClass().getResource("/MainArea.fxml"));
        mainAreaLoader.setController(new MainAreaController());
        //Create bottom bar
        FXMLLoader bottomBarLoader = new FXMLLoader(getClass().getResource("/BottomBar.fxml"));
        bottomBarLoader.setController(new BottomBarController());
        try {
            MenuBar menuBar = menuBarLoader.load();
            ToolBar toolBar = toolBarLoader.load();
            SplitPane mainArea = mainAreaLoader.load();
            HBox bottomBar = bottomBarLoader.load();
            root.getChildren().addAll(menuBar, toolBar, mainArea, bottomBar);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
