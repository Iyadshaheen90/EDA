/**Global methods and variables that all pieces of the program would need access to**/
package com.COMP490.EDA;

import java.io.File;
import java.util.ArrayList;

public final class Global {

    private static String usernameText;
    private static String symbolLoc =("");
//    private static ArrayList<SymbolLibrary> files= new ArrayList<>(20);


    private Global(){}  // Private constructor to prevent instantiation
//    public static void addToArrayList(Project f){
//        files.add(f);
//    }
    public static String getSymbolLoc(){
        return symbolLoc;
    }
    public static void setSymbolLoc(String text){
        symbolLoc = text;
    }

}