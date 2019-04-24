package com.COMP490.EDA;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class SymbolLibrary {
    private String dir;
    private ArrayList<Symbol> symbols;
    public SymbolLibrary (String dir){
        symbols = new ArrayList<>(20);
        this.dir = dir;
    }

    public void addSymbol(Symbol symbol){
        symbols.add(symbol);
    }

    public String getLocation() {return dir;}

    public static void showFiles(File[] files,SymbolLibrary sl) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles(),sl); // Calls same method again.
            } else {
                System.out.println("File: " + file.getName());
            }
        }
    }

    public void removeSymbol(int index){
        symbols.remove(index);
    }

    public void moveSymbol (int newIndex, int oldIndex) {
        Collections.swap(symbols,newIndex,oldIndex);
    }

}
