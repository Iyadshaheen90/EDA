package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class MenuController {
    private TabPane tabArea;
    private Label mouseCoordinates;
    private TreeView tree;
    private String tool;
    private String openSourcePath;
    FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();


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

    // Opens an existing file
    // Bound to File>Open
    @FXML
    public void open()
    {
        Stage stage = new Stage();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            openFile(file);
            //logging the path to test if it is correct
            System.out.println(file.getAbsolutePath());

        }

    }

    //helper function that opens the file that was chosen
    private void openFile(File file) {
        //
        try {
            desktop.open(file);//place holder for now, it opens the file but not with in the program
        } catch (IOException ex) {

        }
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
