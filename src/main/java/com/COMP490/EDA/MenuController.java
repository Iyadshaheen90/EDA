package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private TabPane tabArea;
    private Label mouseCoordinates;
    private TreeView tree;
    private String tool;

    public MenuController(TabPane tabArea, Label mouseCoordinates, TreeView tree, String tool) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.tree=tree;
        this.tool = tool;
    }

    @FXML
    public void newCanvas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/New.fxml"));
        loader.setController(new NewController(tabArea, mouseCoordinates, tree, tool));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("New");
            stage.setScene(new Scene(page, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exits the program
    // Bound to File>Exit
    @FXML
    public void exit() {
        Platform.exit();
    }

    // Opens about window
    // Bound to Help>About
    @FXML
    public void about() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/About.fxml"));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("About");
            stage.setScene(new Scene(page));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
