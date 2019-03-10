package com.COMP490.EDA;

import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class ToolBarController {
    private String shape;
    private Color color;


    public ToolBarController() {
        this.shape = "select";
    }

    public String getTool() {
        return shape;
    }

    public Color getColor()
    {
        if(color==null)
            return color = Color.BLACK;
        else
            return color;
    }

    @FXML
    private ColorPicker colorPicker = new ColorPicker(Color.BLACK);

    @FXML
    void changeColor(ActionEvent event) {
        color = colorPicker.getValue();
        System.out.println("color: "+color);
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
