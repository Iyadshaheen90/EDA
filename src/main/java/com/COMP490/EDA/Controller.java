package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    @FXML
    private VBox root;

    /*
    Initializes the main components of the program.  MainArea needs to be filly loaded first in order
    for the SplitPane to be initialized for the reset of the controllers.  This includes calling load()
    on the FXMLLoader.
     */
    public void initialize() {
        MenuController menuController;
        ToolBarController toolBarController;
        MainAreaController mainAreaController;

        try {
            // Create main area
            FXMLLoader mainAreaLoader = new FXMLLoader(getClass().getResource("/MainArea.fxml"));
            mainAreaController = new MainAreaController();
            mainAreaLoader.setController(mainAreaController);
            VBox mainArea = mainAreaLoader.load();

            // Create toolbar
            FXMLLoader toolBarLoader = new FXMLLoader(getClass().getResource("/ToolBar.fxml"));
            toolBarController = new ToolBarController();
            toolBarLoader.setController(toolBarController);
            ToolBar toolBar = toolBarLoader.load();

            // Create menu bar
            FXMLLoader menuBarLoader = new FXMLLoader(getClass().getResource("/MenuBar.fxml"));
            menuController = new MenuController(
                    mainAreaController.getTabArea(),
                    mainAreaController.getRightStatus(),
                    mainAreaController.getTree(),
                    toolBarController,
                    mainAreaController.getSidepanel());
            menuBarLoader.setController(menuController);
            MenuBar menuBar = menuBarLoader.load();

            root.getChildren().addAll(menuBar, toolBar, mainArea);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
