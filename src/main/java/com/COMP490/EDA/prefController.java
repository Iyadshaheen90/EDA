package com.COMP490.EDA;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class prefController {
    private String symboldir;
    private TreeView<String> symbols;

    @FXML
    private TextField symbolpath;
    private Accordion sidepanel;
    public prefController(String rootDir, Accordion sidepanel){
        symboldir=rootDir;
        this.sidepanel=sidepanel;
    }
    public void initialize(){
        symbolpath.setText(symboldir);
    }
    @FXML
    public void choosefile(){
        DirectoryChooser dc = new DirectoryChooser();
        File a = new File(symboldir);
        dc.setInitialDirectory(a);
        File choice = dc.showDialog(new Stage());
        if(choice == null || ! choice.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Could not open directory");
            alert.setContentText("The file is invalid.");

            alert.showAndWait();
        } else {
            symboldir=choice.getAbsolutePath();
            Global.setSymbolLoc(symboldir);
            symbolpath.setText(Global.getSymbolLoc());
        }
    }
    @FXML
    public void close(){
        File f = new File(Global.getSymbolLoc());
        symbols = new TreeView<>();
        symbols.setRoot(fillExplorer(f));
        this.sidepanel.getPanes().get(0).setContent(symbols);
        //This automatically sets the file explorer open by default
        this.sidepanel.setExpandedPane(this.sidepanel.getPanes().get(0));
        Stage stage = (Stage) symbolpath.getScene().getWindow();
        stage.close();
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
}
