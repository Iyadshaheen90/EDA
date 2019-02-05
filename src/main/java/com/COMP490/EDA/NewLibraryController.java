package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NewLibraryController {
    @FXML
    Button cancelButton;

    /*
    New Library
     */
    @FXML
    public void create() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Root.fxml"));
            loader.setController(new Controller());
            VBox parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            scene.getStylesheets().addAll(getClass().getResource("/Toolbar.css").toExternalForm());
            stage.setTitle("Symbol Editor");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Open Library
    */
    @FXML
    public void open() {
        // TODO
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
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setTitle("Symbol Editor");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
