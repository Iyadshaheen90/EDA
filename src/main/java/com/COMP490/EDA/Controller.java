package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    @FXML
    private VBox root;

    // Instances of all component controllers
    private MenuController menuController;
    private ToolBarController toolBarController;
    private  MainAreaController mainAreaController;
    private  BottomBarController bottomBarController;

    /*
    Initializes the main components of the program.  MainArea needs to be filly loaded first in order
    for the SplitPane to be initialized for the reset of the controllers.  This includes calling load()
    on the FXMLLoader.
     */
    public void initialize() {
        try {
            // Create main area
            FXMLLoader mainAreaLoader = new FXMLLoader(getClass().getResource("/MainArea.fxml"));
            mainAreaController = new MainAreaController();
            mainAreaLoader.setController(mainAreaController);
            SplitPane mainArea = mainAreaLoader.load();

            // Create menu bar
            FXMLLoader menuBarLoader = new FXMLLoader(getClass().getResource("/MenuBar.fxml"));
            menuController = new MenuController(mainAreaController.getTabArea(), null);
            menuBarLoader.setController(menuController);
            MenuBar menuBar = menuBarLoader.load();

            // Create toolbar
            FXMLLoader toolBarLoader = new FXMLLoader(getClass().getResource("/ToolBar.fxml"));
            toolBarController = new ToolBarController();
            toolBarLoader.setController(toolBarController);
            ToolBar toolBar = toolBarLoader.load();

            //Create bottom bar
            FXMLLoader bottomBarLoader = new FXMLLoader(getClass().getResource("/BottomBar.fxml"));
            bottomBarController = new BottomBarController();
            bottomBarLoader.setController(bottomBarController);
            HBox bottomBar = bottomBarLoader.load();
            root.getChildren().addAll(menuBar, toolBar, mainArea, bottomBar);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
