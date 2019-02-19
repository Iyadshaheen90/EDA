package com.COMP490.EDA;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class prefController {
    private String symboldir;
    private TreeView<String> symbols;
    private Map<String, File> listOfFiles;

    @FXML
    private TextField symbolpath;
    private Accordion sidepanel;
    public prefController(String rootDir, Accordion sidepanel){
        symboldir=rootDir;
        this.sidepanel=sidepanel;
        listOfFiles = new HashMap<>(20);
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
            Global.setLibraryLoc(symboldir);
            symbolpath.setText(Global.getLibraryLoc());
        }
    }

    private void addTreeListeners(TreeView<String> symbols) {
        // Coordinate listener
        symbols.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO
                //Help
            }
        });
    }
    @FXML
    public void close(){
        File f = new File(Global.getLibraryLoc());
        symbols = new TreeView<>();
        symbols.setRoot(fillExplorer(f));
        symbols.setEditable(true);
        symbols.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                return new TextFieldTreeCellImpl();
            }
        });
        addTreeListeners(symbols);
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
                listOfFiles.put(f.getName(), f);
            }
        }
        return root;
    }
    private final class TextFieldTreeCellImpl extends TreeCell<String> {
        private TextField textField;

        public TextFieldTreeCellImpl() {
        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }

    }
}

