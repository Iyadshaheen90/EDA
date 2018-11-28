package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class MainAreaController {
    @FXML
    private TabPane tabArea;
    @FXML
    private TreeView treeview;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;

    public TabPane getTabArea() {
        return tabArea;
    }

    public TreeView getTree(){return treeview;}

    public Label getLeftStatus() {
        return leftStatus;
    }

    public void setLeftStatus(Label leftStatus) {
        this.leftStatus = leftStatus;
    }

    public Label getRightStatus() {
        return rightStatus;
    }

    public void setRightStatus(Label rightStatus) {
        this.rightStatus = rightStatus;
    }
}
