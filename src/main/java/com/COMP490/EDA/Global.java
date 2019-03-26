/**Global methods and variables that all pieces of the program would need access to**/
package com.COMP490.EDA;

public final class Global {
//    private static String usernameText;
    private static SymbolLibrary currentSL= new SymbolLibrary("");
    private static String symbolLibLoc =(currentSL.getLocation());
    private static Symbol currentSymbol = new Symbol();

    private Global(){}  // Private constructor to prevent instantiation

    public static void setCurrentSymbol(Symbol currentSymbol) {
        Global.currentSymbol = currentSymbol;
    }
    public static Symbol getCurrentSymbol(){
        return Global.currentSymbol;
    }
    public static String getLibraryLoc(){
        return symbolLibLoc;
    }
    public static void setLibraryLoc(String text){
        System.out.println("Setting " + text + " as current library location.");
        symbolLibLoc = text;
    }
}