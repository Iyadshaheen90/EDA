package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private StackPane mainArea;
    private Label mouseCoordinates;

    public MenuController(StackPane mainArea, Label mouseCoordinates) {
        this.mainArea = mainArea;
        this.mouseCoordinates = mouseCoordinates;
    }

    @FXML
    public void newCanvas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/New.fxml"));
        loader.setController(new NewController(mainArea, mouseCoordinates));
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


    @FXML
    public void exit() {
        Platform.exit();
    }
}
