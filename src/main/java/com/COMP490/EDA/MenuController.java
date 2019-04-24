package com.COMP490.EDA;

import javafx.application.Platform;
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
            if(newValue != null && newValue != oldValue && newValue.isLeaf() && newValue.getValue().endsWith(".eda")){
                System.out.println("Hello World");
//                System.out.println(newValue.getValue());
                System.out.println("new value is " + listOfFiles.get(newValue.getValue()));
                File h = new File(listOfFiles.get(newValue.getValue()).getAbsolutePath());
                System.out.println("loading " + h.getAbsolutePath());
                loadSymbol(h);

            }
        });
        this.sidePanel.getPanes().get(0).setContent(symbols);
        symbols.getRoot().setExpanded(true);
        //This automatically sets the file explorer open by default
        this.sidePanel.setExpandedPane(this.sidePanel.getPanes().get(0));
//      showFiles(rootDir);
        tabArea.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//                Global.setCurrentSymbol(Global.retriveSymbol(Integer.parseInt(observable.getValue().toString())));
            System.out.println(Global.getCurrentSymbol().getName());
            System.out.println(oldValue + " " + newValue);
//                System.out.println(Global.retriveSymbol(Integer.parseInt(observable.getValue().toString())).getName());
//                System.out.println("the symbol shapes are " + Global.getCurrentSymbol().getShapes().toString());
//                System.out.println("The value is " + observable.getValue().toString());
//                tabArea.getTabs().get(Integer.parseInt(observable.getValue().toString())).getContent();
//                System.out.println(tabArea.getTabs().get(Integer.parseInt(observable.getValue().toString())).getContent().toString());
            if ((int)oldValue == -1 || (int)newValue == -1){
                //This is the first tab thats opened, dont do anything.
            }
            else{
                int index = Integer.parseInt(observable.getValue().toString());
                String s = tabArea.getTabs().get(index).getText();
                Global.setCurrentSymbol(Global.retrieveSymbol(s));
//                    Global.setCurrentSymbol(Global.retriveSymbol(Integer.parseInt(observable.getValue().toString())));
                System.out.println("This is a test " + Global.getCurrentSymbol().getName());
                System.out.println("This is a test " + Global.getCurrentSymbol().getShapes().toString());

            }
        });

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

    // Bound to File>Save As
    @FXML
    public void newSave() {
        //dimensions of canvas x
        //array of shapes x
//        Yaml yaml = new Yaml();
        try {
            FileWriter fw = new FileWriter(Global.getLibraryLoc() + "/" + Global.getCurrentSymbol().getName() + ".eda");
            StringWriter writer = new StringWriter();
            Map<String, Object> data = new HashMap<>();

            ArrayList<Shape> fileShapes = Global.getCurrentSymbol().getShapes();
            ArrayList<Shape> shapes = new ArrayList<>();
//            ArrayList<Shape> shapes = new ArrayList<Shape>();
//            ArrayList<Shape> shapes = (ArrayList<Shape>)Global.getCurrentSymbol().getShapes().clone();
            fileShapes.forEach((shape) -> {
                if (shape instanceof Rectangle){
                    //X,Y,ScaleX,ScaleY,Width,Height,Fill
                    Rectangle r = new Rectangle();
                    Rectangle s = (Rectangle) shape;
                    r.setX(s.getX());
                    r.setY(s.getY());
                    r.setScaleX(s.getScaleX());
                    r.setScaleY(s.getScaleY());
                    r.setWidth(s.getWidth());
                    r.setHeight(s.getHeight());
                    r.setFill(s.getFill());
                    System.out.println(s.getFill().toString());
                    shapes.add(r);
                }
                else if (shape instanceof Circle){
                    //centerx,centery, radius
                    Circle s = (Circle) shape;
                    Circle r = new Circle();
                    r.setCenterX(s.getCenterX());
                    r.setCenterY(s.getCenterY());
                    r.setRadius(s.getRadius());
                    r.setFill(s.getFill());
                    System.out.println(s.getFill());
                    shapes.add(r);
                }
                else if (shape instanceof Line){
                    //startX,startY,endX,endY
                    Line s = (Line) shape;
                    Line r = new Line();
                    r.setStartX(s.getStartX());
                    r.setStartY(s.getStartY());
                    r.setEndX(s.getEndX());
                    r.setEndY(s.getEndY());
                    System.out.println(s.getStroke());
                    r.setStroke(s.getStroke());
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
                symbols.getRoot().setExpanded(true);
            });
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

    @FXML
    public void undo() {
        ArrayList<Node> children = Global.getCurrentStateHandler().undo();
        Global.getCurrentSymbol().setDrawArea(children);
    }

    @FXML
    public void redo() {
        ArrayList<Node> children = Global.getCurrentStateHandler().redo();
        Global.getCurrentSymbol().setDrawArea(children);
    }

    public void loadSymbol(File h) {
//        Yaml yaml = new Yaml();
        ArrayList<Shape> shapes = new ArrayList<>();
        int counter = 7;
        int temp = 0;
        String width = null;
        String height = null;
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
                while(currentLine.charAt(counter) != ']'){
                    counter= counter+2;
                    if (currentLine.charAt(counter) == 'L'){
                        counter = LoadLine(counter,currentLine,helper,temp,shapes);
                        //END OF SHAPE LINE
                    }
                    else if (currentLine.charAt(counter) == 'C'){
                        counter = LoadCircle(counter,currentLine,helper,temp,shapes);
                        //Circle
                    }
                    else if (currentLine.charAt(counter) == 'R'){
                        counter = LoadRectangle(counter,currentLine,helper,temp,shapes);
                        //Rectangle

                    }
                    else{
                        System.out.println("Your save file is corrupted Err:Menu307");
                    }
                }
            }
            counter = counter + 9;
            temp = currentLine.indexOf(',', counter);
            helper = currentLine.substring(counter,temp);
            width=helper;
            counter = counter + helper.length() + 9;
            temp = currentLine.indexOf('}', counter);
            helper = currentLine.substring(counter,temp);
            height= helper;
            counter = counter + helper.length() + 2;
            System.out.println(shapes.toString());
//            Global.setCurrentSymbol(new Symbol(tabArea.));
            Pane pane = new Pane();
            pane.setMaxSize(Double.parseDouble(width),Double.parseDouble(height));
            pane.setStyle("-fx-background-color: white");
            Tab tab = new Tab(h.getName() , pane);
            Symbol symbol = new Symbol(h.getName(), pane, Integer.parseInt(width), Integer.parseInt(height), toolBar);
            //Global.addToArrayList(symbol)
            //Global.getSymbolLib(Global.getSymbolLoc()).addSymbol(symbol);
            Global.setCurrentSymbol(symbol);
            Global.getCurrentSymbol().setShapes(shapes);
            Global.getCurrentSymbol().setName(h.getName());
            Global.addToMap(symbol.getName(), symbol);
            System.out.println("I opened");
            tabArea.getTabs().add(tab);
            addCoordinateListener(symbol, pane);
            for(Shape s : shapes){
                symbol.getDrawArea().getChildren().add(s);
            }

        }catch(IOException e){
            System.out.println("Cant create file dude");
            e.printStackTrace();
        }
    }

    public Loadresult<Double> ExtractData(int counter, String currentLine){
        Loadresult<Double> a;
        Double val;
        int temp = currentLine.indexOf(',',counter);
        String helper= currentLine.substring(counter, temp);
        System.out.println("This is the string value" + helper );
//        a.counter = counter + 2 + helper.length();
        counter =counter + 2 + helper.length();
        //Convert string into a Double type
        val = Double.parseDouble(helper);
        a = new Loadresult<>(counter,val);
        return a;
    }

    public Loadresult<Paint> ExtractPaintData(int counter, String currentLine){
        Loadresult<Paint> a;
        Paint val;
        int temp = currentLine.indexOf(',',counter);
        String helper= currentLine.substring(counter, temp);
        System.out.println("This is the string value" + helper );
//        a.counter = counter + 2 + helper.length();
        counter =counter + 2 + helper.length();
        //Convert string into a Double type
        val = Color.valueOf(helper);
        a = new Loadresult<>(counter,val);
        return a;
    }

    public int LoadLine(int counter, String currentLine,String helper,int temp,
                        List<Shape> shapes){
//        Loadresult<Double> res = new Loadresult<Double>();
        counter = counter + 5;
        Line l = new Line();
        counter= counter +7;
        Loadresult<Double> res =ExtractData(counter,currentLine);
        System.out.println(res.result + "this is it");
        counter = res.counter;
        l.setStartX(res.result);
        res = ExtractData(counter=counter+7,currentLine);
        counter = res.counter;
        l.setStartY(res.result);
        res = ExtractData(counter=counter+5,currentLine);
        counter = res.counter;
        l.setEndX(res.result);
        res = ExtractData(counter=counter+5,currentLine);
        counter = res.counter;
        l.setEndY(res.result);
        //Stroke
        Loadresult<Paint> Strokeres;
        Strokeres=ExtractPaintData(counter=counter + 7,currentLine);
        counter = Strokeres.counter;
        l.setStroke(Strokeres.result);
        //Stroke width
        counter = counter + 12;
        temp = currentLine.indexOf(',',counter)-1;
        if (currentLine.charAt(temp-1) == ']'){
            temp--;
        }
        helper=currentLine.substring(counter,temp);
        counter = counter + 1 + helper.length();
        l.setStrokeWidth(Double.parseDouble(helper));
        shapes.add(l);
        return counter;
    }

    public int LoadCircle(int counter,String currentLine, String helper, int temp,
                          List<Shape> shapes){
//        Loadresult<Double> res = ExtractData(counter,currentLine);
        counter = counter +7;
        Circle c = new Circle();
        counter = counter + 8;
        Loadresult<Double> res;

        res = ExtractData(counter,currentLine);
        counter = res.counter;
        c.setCenterX(res.result);
        res = ExtractData(counter = counter + 8, currentLine);
        counter = res.counter;
        c.setCenterY(res.result);
        res = ExtractData(counter=counter + 7,currentLine);
        counter = res.counter;
        c.setRadius(res.result);
        counter = counter + 5;
        temp = currentLine.indexOf(',',counter)-1;
        if (currentLine.charAt(temp-1) == ']'){
            temp--;
        }
        helper = currentLine.substring(counter,temp);
        counter = counter + 1 + helper.length();
        c.setFill(Color.valueOf(helper));
        shapes.add(c);
        return counter;
    }

    public int LoadRectangle(int counter,String currentLine, String helper, int temp,
                             List<Shape> shapes){
//        Loadresult<Double> res = ExtractData(counter,currentLine);
        counter = counter +10;
        Rectangle r = new Rectangle();
        counter = counter + 2;
        Loadresult<Double> res =ExtractData(counter,currentLine);
        counter =res.counter;
        r.setX(res.result);
        res=ExtractData(counter = counter + 2, currentLine);
        System.out.println("this is the value" + currentLine.indexOf(counter));
        counter = res.counter;
        r.setY(res.result);
        res = ExtractData(counter = counter + 6, currentLine);
        System.out.println("this is the value" + currentLine.indexOf(counter));
        counter = res.counter;
        r.setWidth(res.result);
        res = ExtractData(counter = counter + 7, currentLine);
        counter= res.counter;
        r.setHeight(res.result);
        counter = counter + 5;
        temp = currentLine.indexOf(',',counter)-1;
        if (currentLine.charAt(temp-1) == ']'){
            temp--;
        }
        helper = currentLine.substring(counter,temp);
        System.out.println(helper);
        counter=counter + 1 + helper.length();
        r.setFill(Color.valueOf(helper));
        shapes.add(r);
        return counter;
    }

    public class Loadresult<A>{
        public int counter;
        public A result;
        public Loadresult(int counter, A result){
            this.counter=counter;
            this.result=result;
        }
    }
}
