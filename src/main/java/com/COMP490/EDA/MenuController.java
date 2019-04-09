package com.COMP490.EDA;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;
import javafx.scene.shape.Shape;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
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


    public MenuController(TabPane tabArea, Label mouseCoordinates, TreeView tree, ToolBarController toolBar, Accordion sidePanel) {
        this.tabArea = tabArea;
        this.mouseCoordinates = mouseCoordinates;
        this.sidePanel=sidePanel;
        //TODO
        //1.Add a menu to input a file directory where symbols will be stored, this will
        //be the default "rootDir" value. On first startup, this will be blank.
        //2.Make items clickable to open that project. this is more complicated and
        //in order to do this we need to get saving and loading down.
//        rootDir =new File("/home/mrconfus3d/Desktop");
//        System.out.println(rootDir.getAbsolutePath());
        this.toolBar = toolBar;
        listOfFiles = new HashMap<>(20);
        File f = new File(Global.getLibraryLoc());
        symbols = new TreeView<>();
        symbols.setRoot(fillExplorer(f));
        symbols.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue && newValue.isLeaf()){
                System.out.println("Hello World");
//                System.out.println(newValue.getValue());
                System.out.println("new value is " + listOfFiles.get(newValue.getValue()));
                File h = new File(listOfFiles.get(newValue.getValue()).getAbsolutePath());
                System.out.println("loading " + h.getAbsolutePath());
                loadSymbol(h);

            }
        });
        this.sidePanel.getPanes().get(0).setContent(symbols);
        //This automatically sets the file explorer open by default
        this.sidePanel.setExpandedPane(this.sidePanel.getPanes().get(0));
//      showFiles(rootDir);

    }

    private static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getName());
            }
        }
    }

    private TreeItem<String> fillExplorer(File dir){
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

    @FXML
    public void newCanvas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewSymbol.fxml"));
        loader.setController(new NewSymbolController(tabArea, mouseCoordinates, toolBar, sidePanel));
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
//    @FXML
//    public void open()
//    {
//        Stage stage = new Stage();
//        fileChooser.setTitle("Open Resource File");
//        File file = fileChooser.showOpenDialog(stage);
//        if(file!=null)
//        {
//            openFile(file);
//            //logging the path to test if it is correct
//            System.out.println(file.getAbsolutePath());
//
//        }
//
//    }
//
//    //helper function that opens the file that was chosen
//    private void openFile(File file) {
//        //
//        try {
//            desktop.open(file);//place holder for now, it opens the file but not with in the program
//        } catch (IOException ex) {
//
//        }
//    }

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

    // Bound to File>Save As
    @FXML
    public void newSave() {
        //dimensions of canvas x
        //array of shapes x
//        Yaml yaml = new Yaml();
        try {
            FileWriter fw = new FileWriter(Global.getLibraryLoc() + "/" + Global.getCurrentSymbol().getName() + ".txt");
            StringWriter writer = new StringWriter();
            Map<String, Object> data = new HashMap<String, Object>();

            ArrayList<Shape> fileShapes = Global.getCurrentSymbol().getShapes();
            ArrayList<Shape> shapes = new ArrayList<>();
//            ArrayList<Shape> shapes = new ArrayList<Shape>();
//            ArrayList<Shape> shapes = (ArrayList<Shape>)Global.getCurrentSymbol().getShapes().clone();
            for ( int i = 0; i < fileShapes.size() ; i++){
                if (fileShapes.get(i) instanceof Rectangle){
                    //X,Y,ScaleX,ScaleY,Width,Height,Fill
                    Rectangle r = new Rectangle();
                    Rectangle s = (Rectangle) fileShapes.get(i);
                    r.setX(s.getX());
                    r.setY(s.getY());
                    r.setScaleX(s.getScaleX());
                    r.setScaleY(s.getScaleY());
                    r.setWidth(s.getWidth());
                    r.setHeight(s.getHeight());
                    r.setFill(s.getFill());
                    shapes.add(r);
                }
                else if (fileShapes.get(i) instanceof Circle){
                    //centerx,centery, radius
                    Circle s = (Circle) fileShapes.get(i);
                    Circle r = new Circle();
                    r.setCenterX(s.getCenterX());
                    r.setCenterY(s.getCenterY());
                    r.setRadius(s.getRadius());
//                    r.setFill(s.getFill());
                    shapes.add(r);
                }
                else if (fileShapes.get(i) instanceof Line){
                    //startX,startY,endX,endY
                    Line s = (Line) fileShapes.get(i);
                    Line r = new Line();
                    r.setStartX(s.getStartX());
                    r.setStartY(s.getStartY());
                    r.setEndX(s.getEndX());
                    r.setEndY(s.getEndY());
//                    r.setFill(s.getFill());
                    shapes.add(r);
                }
                else{
                    System.out.println("This should not have happened what did you do");
                }
                File f = new File(Global.getLibraryLoc());
                symbols.setRoot(fillExplorer(f));
                symbols.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null && newValue != oldValue && newValue.isLeaf()){
                        System.out.println("Hello World");
//                System.out.println(newValue.getValue());
                        System.out.println("new value is " + listOfFiles.get(newValue.getValue()));
                        File h = new File(listOfFiles.get(newValue.getValue()).getAbsolutePath());
                        System.out.println("loading " + h.getAbsolutePath());
                        loadSymbol(h);

                    }
                });
                this.sidePanel.getPanes().get(0).setContent(symbols);
            }
//            Rectangle rectangle= new Rectangle();
//            rectangle.setX(50);
//            rectangle.setY(91);
//            rectangle.setWidth(113);
//            rectangle.setHeight(110);
//            rectangle.setFill(Color.rgb(1,0,0));
//
//            shapes.add(rectangle);
            System.out.println("The shapes are " + shapes.toString());
            data.put("width" , Global.getCurrentSymbol().getWidth());
            data.put("height" , Global.getCurrentSymbol().getHeight());
            data.put("shapes" ,  shapes);
            System.out.println(data.toString());

//            yaml.dump(data,writer);
//            fw.write(writer.toString());
            fw.write(data.toString());
            fw.close();
//            String output= yaml.dump(data);
//            System.out.println(output);
        }catch(IOException e){
            System.out.println("Cant create file dude");
        }
    }
    public void loadSymbol(File h) {
//        Yaml yaml = new Yaml();
        List<Shape> shapes = new ArrayList<Shape>();
        int counter = 9;
        int temp = 0;
        String helper= null;
        try(BufferedReader reader = new BufferedReader(new FileReader(h))) {
            //shapes=[
//            InputStream input = new FileInputStream(h);
//            Map<String, Object> data = yaml.load(input);
//            System.out.println(data);
            String currentLine = reader.readLine();
            if (currentLine.contains("[]")){
                //no shapes
            }

            else{
                if (currentLine.charAt(counter) == 'L'){
                    Loadresult<Double> res = new Loadresult<Double>();
                    counter = counter + 5;
                    Line l = new Line();
                    counter= counter +7;
                    res =ExtractData(counter,currentLine,res);
                    counter = res.counter;
                    l.setStartX(res.result);
                    res = ExtractData(counter=counter+7,currentLine,res);
                    counter = res.counter;
                    l.setStartY(res.result);
                    res = ExtractData(counter=counter+5,currentLine,res);
                    counter = res.counter;
                    l.setEndX(res.result);
                    res = ExtractData(counter=counter+5,currentLine,res);
                    counter = res.counter;
                    l.setEndY(res.result);
                    counter = counter + 7;
                    //Stroke
                    temp = currentLine.indexOf(',',counter);
                    helper=currentLine.substring(counter,temp);
                    counter = counter + 2 + helper.length();
                    //Stroke width
                    counter = counter + 12;
                    temp = currentLine.indexOf(',',counter)-1;
                    helper=currentLine.substring(counter,temp);
                    counter = counter + 1 + helper.length();
                    shapes.add(l);
                    //END OF SHAPE LINE
                }
            }
        }catch(IOException e){
            System.out.println("Cant create file dude");
            e.printStackTrace();
        }
    }
    public Loadresult ExtractData(int counter, String currentLine,Loadresult a){
        int temp = currentLine.indexOf(',',counter);
        String helper= currentLine.substring(counter, temp);
        a.counter = counter + 2 + helper.length();
        //Convert string into a Double type
        if (a.result instanceof Double){
            a.result=Double.parseDouble(helper);
        }
        //Convert string into a Paint type
        else if (a.result instanceof Paint){
            a.result=Color.valueOf(helper);
        }
        return a;
    }
    public class Loadresult<A>{
        public int counter;
        public A result;
        public Loadresult(){
            counter=0;
            result=null;
        }
    }
}
