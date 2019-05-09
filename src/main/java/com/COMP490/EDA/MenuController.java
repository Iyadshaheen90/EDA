package com.COMP490.EDA;

import com.COMP490.EDA.Memento.StateHandler;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.shape.Shape;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class MenuController {
    private TabPane tabArea;
    private Label mouseCoordinates;
    private ToolBarController toolBar;
    private Accordion sidePanel;
    private TreeView<String> symbols;
    private Map<String, File> listOfFiles;
    private SymbolIO sio;

    public MenuController(TabPane tabArea, Label mouseCoordinates, TreeView tree, ToolBarController toolBar, Accordion sidePanel) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.sidePanel=sidePanel;
        this.toolBar = toolBar;
        this.sio = new SymbolIO(tabArea,mouseCoordinates,toolBar,sidePanel);
    }

    @FXML
    public void newCanvas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewSymbol.fxml"));
        loader.setController(new NewSymbolController(tabArea, mouseCoordinates, toolBar, sidePanel));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("New Symbol");
            stage.setScene(new Scene(page));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exits the program
    // Bound to File>Exit
    @FXML
    public void exit() {
        // Add save confirmation
        Platform.exit();
    }

    @FXML
    public void open() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Open.fxml"));
        loader.setController(new prefController(Global.getLibraryLoc(), sidePanel));
        Stage stage = new Stage();
        try{
            Parent page = loader.load();
            stage.setTitle("Open");
            stage.setScene(new Scene(page));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Opens about window
    // Bound to Help>About
    @FXML
    public void about() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/About.fxml"));
        Stage stage = new Stage();
        try {
            Parent page = loader.load();
            stage.setTitle("About");
            stage.setScene(new Scene(page));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Add coordinate listeners
    private void addCoordinateListener(Symbol file, Pane pane) {
        // Coordinate listener
        pane.setOnMouseMoved(event -> mouseCoordinates.setText((int) event.getX() + ", " + (int) event.getY()));
    }

//    // Bound to File>Save As
    @FXML
    public void newSave(){
        sio.newSave();
    }

    @FXML
    public void undo() {
        ArrayList<Shape> shapes = Global.getCurrentStateHandler().undo();
//        System.out.println(Global.getCurrentStateHandler());
//        System.out.println("In menu undo:" + shapes);
        if(shapes != null) {
            Symbol symbol = Global.getCurrentSymbol();
            symbol.setDrawArea(shapes);
            symbol.setShapes(shapes);
        }
    }

    @FXML
    public void redo() {
        ArrayList<Shape> shapes = Global.getCurrentStateHandler().redo();
//        System.out.println(Global.getCurrentStateHandler());
//        System.out.println("In menu undo:" + shapes);
        if(shapes != null) {
            Symbol symbol = Global.getCurrentSymbol();
            symbol.setDrawArea(shapes);
            symbol.setShapes(shapes);
        }
    }
}
