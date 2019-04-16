package com.COMP490.EDA;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MainAreaController {
    @FXML
    private TabPane tabArea;
    @FXML
    private TreeView treeview;
    @FXML
    private Label leftStatus;
    @FXML
    private Accordion sidepanel;
    @FXML
    private Label rightStatus;

    @FXML
    public VBox propertiesItems;

    @FXML
    private Label shapeLabel;

    public TabPane getTabArea() {
        return tabArea;
    }

    public TreeView getTree(){return treeview;}

    public Accordion getSidepanel(){return sidepanel;}

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
