package com.COMP490.EDA;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SymbolLoader {
    private TabPane tabArea;
    private Label mouseCoordinates;
    private ToolBarController toolBar;
    private Accordion sidePanel;

    public SymbolLoader(TabPane tabArea, Label mouseCoordinates, ToolBarController toolBar, Accordion sidePanel) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.toolBar = toolBar;
        this.sidePanel = sidePanel;
    }

    public void loadSymbol(String name, int width, int height) {
        Pane pane = new Pane();
        pane.setMaxSize(width, height);
        pane.setStyle("-fx-background-color: white");
        Symbol symbol = new Symbol(name, pane, width, height, toolBar, sidePanel);
        Global.setCurrentSymbol(symbol);
        Global.addToMap(name, symbol);
        Tab tab = new Tab(name , pane);
        addTabListeners(tab);
        tabArea.getTabs().add(tab);
        tabArea.getSelectionModel().select(tab);
        addCoordinateListener(symbol, pane);
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
                    sidePanel.setExpandedPane(sidePanel.getPanes().get(0));
                    AnchorPane pane = (AnchorPane) sidePanel.getPanes().get(1).getContent();
                    pane.getChildren().get(0).setVisible(false);

                    Global.removeSymbol(Global.getCurrentSymbol().getName());
                }
            }
        });
    }
}
