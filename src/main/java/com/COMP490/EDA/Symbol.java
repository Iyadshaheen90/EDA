package com.COMP490.EDA;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Symbol {

    private String name;
    private int width;
    private int height;
    private double initialX;
    private double initialY;

//    MainAreaController mac = new MainAreaController();

    private AnchorPane properties;//this is to access the properties pane inside the sidepanel and be able to set its
    // content to true. this is used in drawListeners, the select part of the decision statement, for a better
    // understanding visit that
    private VBox vbox;
    private Accordion sidePanel;
    private Pane drawArea;
    private Shape shape;
    public int shapeIndexInShapes;
    private ToolBarController toolBar;
    private boolean clicked = false;
    //Keep track of all shapes on pane
    private ArrayList<Shape> shapes;
    private Drawable draw;

    public Symbol(){
        //Empty Symbol
    }

    public Symbol(String name, Pane drawArea, int width, int height , ToolBarController toolBar, Accordion sidePanel) {
        this.name = name;
        this.drawArea = drawArea;
        this.width = width;
        this.height = height;
        this.toolBar = toolBar;
        this.sidePanel = sidePanel;
        properties = (AnchorPane) sidePanel.getPanes().get(1).getContent();
        vbox = (VBox) properties.getChildren().get(0);
        shapes = new ArrayList<>();
        draw = new Drawable(drawArea, toolBar.getTool());
        initialize();
    }

    public void setDrawArea(ArrayList<Shape> newShapes) {
//        System.out.println("In setDrawArea.  New shapes " + shapes);
//        System.out.println("In Draw Area. Old shapes" + this.shapes);
        this.drawArea.getChildren().removeAll(this.shapes);
        this.drawArea.getChildren().addAll(newShapes);
    }
    public Drawable getDraw(){ return this.draw;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setShapes(ArrayList<Shape> shapes){
        this.shapes=new ArrayList<>(shapes);
        System.out.println("Shapes is now " + this.shapes);
    }

    public Pane getDrawArea(){ return this.drawArea; }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void initialize() {
        drawBackground();
        addMouseScrolling(drawArea);
        addDragListeners(drawArea);
        addDrawListeners();
        addPropertyListeners();
    }


    // draw function for the background grid
    private void drawBackground() {
        //Draw background rows
        for(int i=0; i<= height ; i=i+20){
            Line l = new Line();
            l.setStartX(0);
            l.setEndX(width);
            l.setStartY(i);
            l.setEndY(i);
            l.setStrokeWidth(0.2);
            drawArea.getChildren().add(l);
        }
        //draw background columns
        for(int i=0; i<= width ; i=i+20){
            Line l = new Line();
            l.setStartX(i);
            l.setEndX(i);
            l.setStartY(0);
            l.setEndY(height);
            l.setStrokeWidth(0.2);
            drawArea.getChildren().add(l);
        }
    }

    // function for scrolling and scaling of the pane
    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            System.out.println(deltaY);
            if (deltaY < 0){
                zoomFactor = 2-zoomFactor;
                node.setScaleX(node.getScaleX() * zoomFactor);
                node.setScaleY(node.getScaleY() * zoomFactor);
            }
            if (deltaY > 0){
                node.setScaleX(node.getScaleX() * zoomFactor);
                node.setScaleY(node.getScaleY() * zoomFactor);
            }
        });
    }

    // function to allow dragging in the editable area
    private void addDragListeners(final Node n) {
        n.setOnMousePressed(me -> {
            if(me.getButton()!=MouseButton.MIDDLE && me.isControlDown()
                    && toolBar.getTool().equals("select"))
            {
                initialX = me.getX();
                initialY = me.getY();
            }

        });

        n.setOnMouseDragged(me -> {
            if(me.getButton()!=MouseButton.MIDDLE && me.isControlDown()
                    && toolBar.getTool().equals("select"))
            {
                n.setTranslateX(me.getX() + n.getTranslateX() - initialX);
                n.setTranslateY(me.getY() + n.getTranslateY() - initialY);
            }
        });
    }

    private void disableAllProperties(boolean flag) {
        vbox.getChildren().get(9).setDisable(flag);
        vbox.getChildren().get(11).setDisable(flag);
        vbox.getChildren().get(13).setDisable(flag);
        vbox.getChildren().get(15).setDisable(flag);
    }

    private void disableSelectedProperties(boolean flag) {
        vbox.getChildren().get(9).setDisable(!flag);
        vbox.getChildren().get(11).setDisable(!flag);
        vbox.getChildren().get(13).setDisable(flag);
        vbox.getChildren().get(15).setDisable(flag);
        vbox.getChildren().get(18).setDisable(flag);
    }

    private void setShapeProperties(Shape shape) {
        String shapeID = shape.getId();
        Label shapeLabel = new Label("Shape");
        //.......................
        //for resetting the start x label to say width and the end X label to say height when rect is selected
        Label label1 = new Label();
        label1.setFont(Font.font(18));
        Label label2 = new Label();
        label2.setFont(Font.font(18));
        //.....................
        TextField strokeTextField = (TextField) vbox.getChildren().get(3);
        TextField startXTextField = (TextField) vbox.getChildren().get(9);
        startXTextField.setText("");
        TextField endXTextField = (TextField) vbox.getChildren().get(11);
        endXTextField.setText("");
        TextField startYTextField = (TextField) vbox.getChildren().get(13);
        startYTextField.setText("");
        TextField endYTextField = (TextField) vbox.getChildren().get(15);
        endYTextField.setText("");
        TextField radiusTextField = (TextField) vbox.getChildren().get(18);
        radiusTextField.setText("");
        Slider strokeSlider = (Slider)vbox.getChildren().get(4);
        strokeSlider.setValue(shape.getStrokeWidth());
        strokeTextField.setText(String.format("%.2f",shape.getStrokeWidth()));
        ColorPicker colorPicker = (ColorPicker)vbox.getChildren().get(6);

//        strokeTextField.setText(shape.getStroke().toString());
        shapeLabel.setFont(Font.font(18));

        //edit the shape name label to the name of this shape
        switch (shapeID)
        {
            case "Circle":
                shapeLabel.setText("Shape: Circle");

                vbox.getChildren().get(18).setDisable(false);//enable radius field
                disableAllProperties(true);//disable properties that are not useful like start x, etc

                radiusTextField.setText(String.format("%.2f",((Circle) shape).getRadius()));
                colorPicker.setValue((Color) shape.getFill());

                vbox.getChildren().set(0,shapeLabel);
                vbox.getChildren().set(3,strokeTextField);
                vbox.getChildren().set(4,strokeSlider);
                vbox.getChildren().set(6,colorPicker);
                vbox.getChildren().set(18,radiusTextField);
                break;

            case "Rectangle":
                shapeLabel.setText("Shape: Rectangle");

                disableSelectedProperties(true);

                colorPicker.setValue((Color) shape.getFill());
                label1.setText("Width");
                label2.setText("Height");
                startXTextField.setPromptText("Width");
                endXTextField.setPromptText("Height");
                startXTextField.setText(String.format("%.2f",((Rectangle) shape).getWidth()));//techinicaly width
                endXTextField.setText(String.format("%.2f",((Rectangle) shape).getHeight()));//techinicaly height
                vbox.getChildren().set(0,shapeLabel);
                vbox.getChildren().set(3,strokeTextField);
                vbox.getChildren().set(4,strokeSlider);
                vbox.getChildren().set(6,colorPicker);
                vbox.getChildren().set(8, label1);
                vbox.getChildren().set(10, label2);
                vbox.getChildren().set(9,startXTextField);
                vbox.getChildren().set(11,endXTextField);


                break;

            case "Line":
                shapeLabel.setText("Shape: Line");

                disableAllProperties(false);
                vbox.getChildren().get(18).setDisable(true);//disable radius field
                label1.setText("StartX");
                label2.setText("EndX");
                startXTextField.setPromptText("Start X Position");
                endXTextField.setPromptText("End X Position");
                startXTextField.setText(String.format("%.2f",((Line) shape).getStartX()));
                endXTextField.setText(String.format("%.2f" ,((Line) shape).getEndX()));
                startYTextField.setText(String.format("%.2f",((Line) shape).getStartY()));
                endYTextField.setText(String.format("%.2f",((Line) shape).getEndY()));
                colorPicker.setValue((Color) shape.getStroke());

                vbox.getChildren().set(0,shapeLabel);
                vbox.getChildren().set(3,strokeTextField);
                vbox.getChildren().set(4,strokeSlider);
                vbox.getChildren().set(6,colorPicker);
                vbox.getChildren().set(8, label1);
                vbox.getChildren().set(10, label2);
                vbox.getChildren().set(9,startXTextField);
                vbox.getChildren().set(11,endXTextField);
                vbox.getChildren().set(13,startYTextField);
                vbox.getChildren().set(15,endYTextField);
                break;

                default:
                    break;
        }
    }

    public void addPropertyListeners(){
        Slider strokeSlider =  (Slider)vbox.getChildren().get(4);
        TextField strokeTextField = (TextField)vbox.getChildren().get(3);
        ColorPicker colorPicker = (ColorPicker)vbox.getChildren().get(6);
        TextField startXTextField = (TextField) vbox.getChildren().get(9);
        TextField endXTextField = (TextField) vbox.getChildren().get(11);
        TextField startYTextField = (TextField) vbox.getChildren().get(13);
        TextField endYTextField = (TextField) vbox.getChildren().get(15);
        TextField radiusTextField = (TextField) vbox.getChildren().get(18);

        strokeSlider.valueChangingProperty().addListener((observableValue, aBoolean, t1) -> {
            strokeTextField.setText(String.format("%.2f", strokeSlider.getValue()));
            shape.setStrokeWidth(strokeSlider.getValue());
        });

        colorPicker.setOnAction(actionEvent -> {
            if (shape.getId().equals("Line"))
                shape.setStroke(colorPicker.getValue());
            if (shape.getId().equals("Rectangle") || shape.getId().equals("Circle"))
                shape.setFill(colorPicker.getValue());
        });

        strokeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !(Double.parseDouble(newValue) < 0)) {
                shape.setStrokeWidth(Double.parseDouble(newValue));
                strokeSlider.setValue(Double.parseDouble(strokeTextField.getText()));

            }
        });

        startXTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (shape.getId().equals("Line") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Line) shape).setStartX(20*Math.round(Double.parseDouble(newValue)/20));
            if (shape.getId().equals("Rectangle") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Rectangle) shape).setWidth(20*Math.round(Double.parseDouble(newValue)/20));
        });

        endXTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (shape.getId().equals("Line") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Line) shape).setEndX(20*Math.round(Double.parseDouble(newValue)/20));
            if (shape.getId().equals("Rectangle") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Rectangle) shape).setHeight(20*Math.round(Double.parseDouble(newValue)/20));
        });

        startYTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (shape.getId().equals("Line") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Line) shape).setStartY(20*Math.round(Double.parseDouble(newValue)/20));
        });

        endYTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (shape.getId().equals("Line") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Line) shape).setEndY(20*Math.round(Double.parseDouble(newValue)/20));
        });

        radiusTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (shape.getId().equals("Circle") && !newValue.isEmpty() && !(Double.parseDouble(newValue) < 0))
                ((Circle) shape).setRadius(20*Math.round(Double.parseDouble(newValue)/20));
        });
    }

    public void addDrawListeners() {
        drawArea.setOnMouseClicked(event -> {
            drawArea.requestFocus();//focusing on draw area so if the user hits escape it will operate
//                System.out.println(shapes.size());//testing if the shape is getting deleted when escape is pressed
            draw.updateTool(toolBar.getTool());

            //if select is the tool selected we capture the point where the user clicked and iterate through the
            //shapes checking if it contains the point. if it does we break out of the loop and when delete is
            //pressed we remove the shape from the arraylist and from the children of the pane
            if (toolBar.getTool().equals("select") && !event.isControlDown()) {
                if(shape!=null)//if the shape is not null then a shape is selected and therefore...
                    shape.setEffect(null);//removing the previously selected shape effect, if any
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (int i = 0; i < shapes.size(); i++) {  // check shapes from front to back
                    shape = shapes.get(i);
                    if (shape.getBoundsInParent().contains(x, y)) {
                        //expanding the pane of properties
                        sidePanel.setExpandedPane(sidePanel.getPanes().get(1));
                        //set the vbox to true which will cause the properties to appear
                        properties.getChildren().get(0).setVisible(true);
                        setShapeProperties(shape);
                        shapeIndexInShapes = i;
                        shape.setEffect(new DropShadow());// setting a shape effect to know what shape is selected
                        System.out.println("Stroke width is: "+shapes.get(shapeIndexInShapes).getStrokeWidth());
                        break;
                    } else {
                        //if a shape is not selected then we set the shape to null because it is
                        // currently set to some shape but we did not select a shape. therefore, if we press delete
                        //then the last shape in the arraylist will be deleted, to dodge that bug, we set the shape
                        // to null if the coordinates do not lie within any of the shapes
                        shape = null;
                        //if we used the select and clicked outside a shape, then the properties are set back to
                        //invisible
                        properties.getChildren().get(0).setVisible(false);
                    }
                }

            }
            if (clicked) {
                // add shape
                draw.drawShape(20*(Math.round(event.getX()/20)), 20*(Math.round(event.getY()/20)), toolBar.getColor());
                //remove onMouseMove handler
                clicked = false;
            } else if (toolBar.getTool().equals("circle") || toolBar.getTool().equals("line")
                    || toolBar.getTool().equals("rectangle")) {
                //set shape start point
                draw.setStartPoint(20*(Math.round(event.getX()/20)), 20*(Math.round(event.getY()/20)));
                //add onMouseMove handler
//                    drawArea.setOnMouseMoved(new EventHandler<MouseEvent>() {
//                        @Override
//                        public void handle(MouseEvent event) {
//                            drawShape(event.getX(), event.getY());
//                        }
//                    });
                clicked = true;
            }

            drawArea.setOnMouseMoved(mouseEvent -> {
                if (clicked) {
                    draw.shapePreview(mouseEvent, toolBar.getColor());
                }
            });
        });

        drawArea.setOnKeyPressed(keyEvent -> {
            if (clicked && keyEvent.getCode() == KeyCode.ESCAPE) {
                System.out.println("escape pressed");
                clicked = false;
                draw.exitDrawing();
            }
            //
            if (toolBar.getTool().equals("select") && keyEvent.getCode().equals(KeyCode.DELETE) && shape != null);
            {
                if (!keyEvent.getCode().equals(KeyCode.ESCAPE) && !keyEvent.getCode().equals(KeyCode.CONTROL)) {
                    System.out.println("delete pressed");
                    drawArea.getChildren().remove(shape);
                    shapes.remove(shape);
                    properties.getChildren().get(0).setVisible(false);
                }
            }

        });
    }

    // Shape arraylist controls
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    // Might change depending on how we want to use
    public Shape getShape(Shape shape) {
        return shapes.get(shapes.indexOf(shape));
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }
}