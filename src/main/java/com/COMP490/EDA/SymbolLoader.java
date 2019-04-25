package com.COMP490.EDA;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
        tab.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CloseConfirm.fxml"));
                CloseConfirmController closeConfirmController = new CloseConfirmController();
                loader.setController(closeConfirmController);
                Stage stage = new Stage();
                try {
                    Parent page = loader.load();
                    stage.setTitle("Close");
                    stage.setScene(new Scene(page));
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
