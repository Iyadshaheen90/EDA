/**Global methods and variables that all pieces of the program would need access to**/
package com.COMP490.EDA;

import com.COMP490.EDA.Memento.StateHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Global {
    private static Map<String, Symbol> aMap = new HashMap<String, Symbol>();
    private static SymbolLibrary currentSL= new SymbolLibrary("");
    private static String symbolLibLoc =(currentSL.getLocation());
    private static Symbol currentSymbol = new Symbol();
    private static Map<Symbol, StateHandler> state  = new HashMap<>();

    private Global(){}  // Private constructor to prevent instantiation

    public static void setCurrentSymbol(Symbol currentSymbol) {
        Global.currentSymbol = currentSymbol;
        Global.state.put(currentSymbol,  new StateHandler());
    }

    public static Symbol getCurrentSymbol(){
        return Global.currentSymbol;
    }

    public static String getLibraryLoc(){
        return symbolLibLoc;
    }

    public static void setLibraryLoc(String text) {
        symbolLibLoc = text;
    }

    public static StateHandler getCurrentStateHandler() {
        return state.get(Global.currentSymbol);
    }

    public static void addToMap(String i , Symbol k){
        aMap.put(i,k);
    }
    public static Symbol retriveSymbol(String i){
        return aMap.get(i);
    }
}