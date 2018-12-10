package com.COMP490.EDA;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class MenuController {
    private TabPane tabArea;
    private Label mouseCoordinates;
    private ToolBarController toolBar;
    private Accordion sidePanel;
    FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();
    private HostServices hs;
//    private File rootDir;
    private TreeView<String> symbols;
    public MenuController(TabPane tabArea, Label mouseCoordinates, TreeView tree, ToolBarController toolBar, Accordion sidePanel) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.sidePanel=sidePanel;
        this.hs=hs;
        //TODO
        //1.Add a menu to input a file directory where symbols will be stored, this will
        //be the default "rootDir" value. On first startup, this will be blank.
        //2.Make items clickable to open that project. this is more complicated and
        //in order to do this we need to get saving and loading down.
//        rootDir =new File("/home/mrconfus3d/Desktop");
//        System.out.println(rootDir.getAbsolutePath());
        Global.setSymbolLoc("/");
        this.toolBar = toolBar;
        if (Global.getSymbolLoc().equals("/")){

        }
        else{
            File f = new File(Global.getSymbolLoc());
            symbols = new TreeView<>();
            symbols.setRoot(fillExplorer(f));
            this.sidePanel.getPanes().get(0).setContent(symbols);
            //This automatically sets the file explorer open by default
            this.sidePanel.setExpandedPane(this.sidePanel.getPanes().get(0));
//        showFiles(rootDir);

        }


    }
    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getName());
            }
        }
    }

    public TreeItem<String> fillExplorer(File dir){
        TreeItem<String> root = new TreeItem<>(dir.getName());
        for (File f : dir.listFiles()){
            if (f.isDirectory()){
                root.getChildren().add(fillExplorer(f));

            }
            else{
                root.getChildren().add(new TreeItem<>(f.getName()));

            }
        }
        return root;
    }

    @FXML
    public void newCanvas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/New.fxml"));
        loader.setController(new NewController(tabArea, mouseCoordinates, toolBar, sidePanel));
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

    // Exits the program
    // Bound to File>Exit
    @FXML
    public void exit() {
        Platform.exit();
    }

    // Opens an existing file
    // Bound to File>Open
    @FXML
    public void open()
    {
        Stage stage = new Stage();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null)
        {
            openFile(file);
            //logging the path to test if it is correct
            System.out.println(file.getAbsolutePath());

        }

    }

    //helper function that opens the file that was chosen
    private void openFile(File file) {
        //
        try {
            desktop.open(file);//place holder for now, it opens the file but not with in the program
        } catch (IOException ex) {

        }
    }

    @FXML
    public void openPref() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/preferences.fxml"));
        loader.setController(new prefController(Global.getSymbolLoc(), sidePanel));
        Stage stage = new Stage();
        try{
            Parent page = loader.load();
            stage.setTitle("Preferences");
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
}
