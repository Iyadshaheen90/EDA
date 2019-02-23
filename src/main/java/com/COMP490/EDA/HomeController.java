package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/*
Controller for the initial home screen.
    New button to open NewLibrary window
    Open Button to open filechooser to select library
    Exit button to exit program

Last edited by: John Brehm 2/12
 */

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
            AnchorPane newLibrary = newLibraryController.load();
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

        if(libraryLocation != null) {
//      TODO: Move this part to other function duplicated in NewLibraryController
            Global.setLibraryLoc(libraryLocation.getAbsolutePath());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Root.fxml"));
                loader.setController(new Controller());
                VBox parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) newButton.getScene().getWindow();
                scene.getStylesheets().addAll(getClass().getResource("/Toolbar.css").toExternalForm());
                stage.setTitle("Symbol Editor");
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
