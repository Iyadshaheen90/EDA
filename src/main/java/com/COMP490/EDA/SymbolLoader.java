package com.COMP490.EDA;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SymbolLoader {
    private TabPane tabArea;
    private Label mouseCoordinates;
    private ToolBarController toolBar;

    public SymbolLoader(TabPane tabArea, Label mouseCoordinates, ToolBarController toolBar) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.toolBar = toolBar;
    }

    public void loadSymbol(String name, int width, int height) {
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setStyle("-fx-background-color: white");
        Tab tab = new Tab(name , pane);
        addTabListeners(tab);
        Symbol symbol = new Symbol(name, pane, width, height, toolBar);
        tabArea.getTabs().add(tab);
        addCoordinateListener(symbol, pane);
        Global.setCurrentSymbol(symbol);
        Global.addToMap(name, symbol);
    }

    // Add coordinate listeners
    private void addCoordinateListener(Symbol file, Pane pane) {
        // Coordinate listener
        pane.setOnMouseMoved(event -> mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY()));
    }

    private void addTabListeners(Tab tab) {
        tab.setOnCloseRequest(event -> {
            if(Global.getCurrentStateHandler().getStateNum() > 0) {
                Alert closeConfirmation = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "You have unsaved changes.\nAre you sure you want to exit?"
                );
                Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                        ButtonType.OK
                );
                exitButton.setText("Close");
                closeConfirmation.setHeaderText("Confirm Exit");
                Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
                if (!ButtonType.OK.equals(closeResponse.get())) {
                    event.consume();
                } else {
                    Global.removeSymbol(Global.getCurrentSymbol().getName());
                }
            }
        });
    }
}
