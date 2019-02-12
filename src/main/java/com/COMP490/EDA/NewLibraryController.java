package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/*
Controller for new library window
    Textbox for name entry
    Textbox for path entry
    Browse button for browsing to path
    Open button for confirm
    Cancel button to return to home screen

Last edited by: John Brehm 2/12
 */

public class NewLibraryController {
    @FXML
    TextField fileName;
    @FXML
    TextField filePath;
    @FXML
    Text errorText;

    /*
    New Library
    Creates a new library in the specified location
     */
    @FXML
    public void create() {
        File file = new File(filePath.getText());
        if (!file.exists()) {
            errorText.setVisible(true);
        }
        if(!file.isDirectory()) { // Checks to make sure file is directory
            file = file.getParentFile(); // Gets parent if it is a file
        }

//      TODO: Add part which will load it in memory and set up the library in memory

//      TODO: Move this part to other function which open in HomeController can call
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Root.fxml"));
            loader.setController(new Controller());
            VBox parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) filePath.getScene().getWindow();
            scene.getStylesheets().addAll(getClass().getResource("/Toolbar.css").toExternalForm());
            stage.setTitle("Symbol Editor");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Open filechooser to select which library to open
    libraries are represented by folders so a folder will be selected
    */
    @FXML
    public void browse() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Library");
        File file = directoryChooser.showDialog(new Stage());
        if(file != null) {
            filePath.setText(file.getAbsolutePath());
        }
    }

    /*
    Exit the window
     */
    @FXML
    public void exit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            loader.setController(new HomeController());
            Pane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = (Stage) filePath.getScene().getWindow();
            stage.setTitle("Symbol Editor");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
