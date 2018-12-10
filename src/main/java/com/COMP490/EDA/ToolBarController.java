package com.COMP490.EDA;

import javafx.fxml.FXML;

public class ToolBarController {
    private String shape;

    public ToolBarController() {
        this.shape = "select";
    }

    public String getTool() {
        return shape;
    }

    @FXML
    public void setSelect() {
        shape = "select";
        System.out.println(getTool());
    }

    @FXML
    public void moveShape() {
        shape = "move";
        System.out.println(getTool());
    }

    @FXML
    public void setLine() {
        shape = "line";
        System.out.println(getTool());
    }

    @FXML
    public void setRectangle() {
        shape = "rectangle";
        System.out.println(getTool());
    }

    @FXML
    public void setCircle() {
        shape = "circle";
        System.out.println(getTool());
    }
}
