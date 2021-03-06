/*Global methods and variables that all pieces of the program would need access to*/
package com.COMP490.EDA;

import com.COMP490.EDA.Memento.StateHandler;

import java.util.HashMap;
import java.util.Map;

public final class Global {
    private static Map<String, Symbol> aMap = new HashMap<>();
    private static SymbolLibrary currentSL= new SymbolLibrary("");
    private static String symbolLibLoc =(currentSL.getLocation());
    private static Symbol currentSymbol;
    private static Map<String, StateHandler> state  = new HashMap<>();

    private Global(){}  // Private constructor to prevent instantiation

    public static void setCurrentSymbol(Symbol currentSymbol) {
        System.out.println("Setting currentSymbol to " + currentSymbol.getName());
        Global.currentSymbol = currentSymbol;
        // If the name isn't already added add it
        if(!state.containsKey(currentSymbol.getName())) {
            System.out.println("Creating new StateHandler for " + currentSymbol.getName());
            state.put(currentSymbol.getName(), new StateHandler(currentSymbol.getShapes()));
            // statenum is correct but the list is wrong
        }
    }

    public static Symbol getCurrentSymbol(){
        return currentSymbol;
    }

    public static String getLibraryLoc(){
        return symbolLibLoc;
    }

    public static void setLibraryLoc(String text) {
        symbolLibLoc = text;
    }

    public static StateHandler getCurrentStateHandler() {
        return state.get(currentSymbol.getName());
    }

    public static void removeSymbol(String name) {
        System.out.println("Removing " + name + " from maps.");
        state.remove(name);
        aMap.remove(name);
    }

    public static void addToMap(String i , Symbol k){
        System.out.println("Adding " + i + " to aMap.");
        aMap.put(i,k);
    }
    public static void removeFromMap(String i){
        System.out.println("removing " + i );
        aMap.remove(i);
    }

    public static Symbol retrieveSymbol(String i){
        System.out.println("Getting " + i + " from aMap.");
        return aMap.get(i);
    }
}