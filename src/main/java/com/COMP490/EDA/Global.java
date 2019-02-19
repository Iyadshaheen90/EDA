/**Global methods and variables that all pieces of the program would need access to**/
package com.COMP490.EDA;

public final class Global {

    private static String usernameText;
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

    public static String getLibraryLoc(){
        return symbolLibLoc;
    }
    public static void setLibraryLoc(String text){
        System.out.println("Setting " + text + " as current library location.");
        symbolLibLoc = text;
    }
//    public static void addSymbolLib(String addr, SymbolLibrary sl) {
//        symbolLibs.put(addr, sl);
//    }
//    public static SymbolLibrary getSymbolLib(String addr){
//         return symbolLibs.get(addr);
//    }
}