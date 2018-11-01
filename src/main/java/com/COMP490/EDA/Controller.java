package com.COMP490.EDA;

import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Label mouseCoordinates;
    @FXML
    private ScrollPane mainArea;


    private Canvas canvas;

    public void initialize() {

    }

    @FXML
    public void newCanvas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/New.fxml"));
            loader.setController(new NewController(canvas));
            Stage stage = new Stage();
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
