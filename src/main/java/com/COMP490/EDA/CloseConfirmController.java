package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CloseConfirmController {
    @FXML
    Text text;

    public CloseConfirmController() { }

    public void initialize() {
        text.getText().replace("$$", Global.getCurrentSymbol().getName());
    }

    private void exit() {
        Stage stage = (Stage) text.getScene().getWindow();
        stage.close();
    }

    // close window and tab
    @FXML
    public void close() {
        // close tab
        exit();
    }

    // Close window
    @FXML
    public void cancel() {
        exit();
    }

    // Save shape, close window, and close tab
    @FXML
    public void save() {
        // save logic
        exit();
    }
}
