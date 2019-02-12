/**Global methods and variables that all pieces of the program would need access to**/
package com.COMP490.EDA;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public final class Global {

    private static String usernameText;
//    private static HashMap<String, SymbolLibrary> symbolLibs= new HashMap<String,SymbolLibrary>();
    private static SymbolLibrary currentSL= new SymbolLibrary("");
    private static String symbolLibLoc =(currentSL.getLocation());

    private static Project currentProj = new Project();
    public static void setCurrentProj(Project currentProj) {
        Global.currentProj = currentProj;
    }
    public static Project getCurrentProj(){
        return Global.currentProj;
    }
    //private static ArrayList<SymbolLibrary> files= new ArrayList<>(20);
    private Global(){}  // Private constructor to prevent instantiation
    //public static void addToArrayList(Project f){
    //    files.add(f);
    //}
    public static String getSymbolLoc(){
        return symbolLibLoc;
    }
    public static void setSymbolLoc(String text){
        symbolLibLoc = text;
    }
//    public static void addSymbolLib(String addr, SymbolLibrary sl) {
//        symbolLibs.put(addr, sl);
//    }
//    public static SymbolLibrary getSymbolLib(String addr){
//         return symbolLibs.get(addr);
//    }
}