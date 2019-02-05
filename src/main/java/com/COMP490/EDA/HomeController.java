package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HomeController {
    @FXML
    Button newButton;

    /*
    Open dialog to enter name of new library
     */
    @FXML
    public void newLibrary() {
        try {
            FXMLLoader newLibraryController = new FXMLLoader(getClass().getResource("/NewLibrary.fxml"));
            newLibraryController.setController(new NewLibraryController());
            VBox newLibrary = newLibraryController.load();
            Scene scene = new Scene(newLibrary);
            Stage newLibraryWindow = (Stage) newButton.getScene().getWindow();
            newLibraryWindow.setTitle("New");
            newLibraryWindow.setScene(scene);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Open filechooser to select which library to open
    libraries are represented by folders so a folder will be selected
    */
    @FXML
    public void open() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Library");
        File libraryLocation = directoryChooser.showDialog(new Stage());
        // use folderpath to open library
    }

    /*
    Exit the program
    TODO: May want to add confirmation in the future
     */
    @FXML
    public void exit() {
        Platform.exit();
    }
}
