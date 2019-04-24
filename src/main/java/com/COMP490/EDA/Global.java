/**Global methods and variables that all pieces of the program would need access to**/
package com.COMP490.EDA;

import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Global {

    private static String usernameText;
    private static List<Symbol> symbols = new ArrayList<>();
    private static Map<String, Symbol> aMap = new HashMap<String, Symbol>();
//    private static HashMap<String, SymbolLibrary> symbolLibs= new HashMap<String,SymbolLibrary>();
    private static SymbolLibrary currentSL= new SymbolLibrary("");
    private static String symbolLibLoc =(currentSL.getLocation());

    private static Symbol currentSymbol = new Symbol();
    public static void setCurrentSymbol(Symbol currentSymbol) {
        Global.currentSymbol = currentSymbol;
    }
    public static Symbol getCurrentSymbol(){
        return Global.currentSymbol;
    }
    //private static ArrayList<SymbolLibrary> files= new ArrayList<>(20);
    private Global(){}  // Private constructor to prevent instantiation
    //public static void addToArrayList(Symbol f){
    //    files.add(f);
    //}
    private static void addSymbol(Symbol symbol){
        symbols.add(symbol);
    }
    public static String getLibraryLoc(){
        return symbolLibLoc;
    }
    public static void setLibraryLoc(String text){
        System.out.println("Setting " + text + " as current library location.");
        symbolLibLoc = text;
    }
    public static void addToMap(String i , Symbol k){
        aMap.put(i,k);
    }
    public static Symbol retriveSymbol(String i){
        return aMap.get(i);
    }
//    public static void addSymbolLib(String addr, SymbolLibrary sl) {
//        symbolLibs.put(addr, sl);
//    }
//    public static SymbolLibrary getSymbolLib(String addr){
//         return symbolLibs.get(addr);
//    }
}