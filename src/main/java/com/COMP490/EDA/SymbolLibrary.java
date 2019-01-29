package com.COMP490.EDA;

import java.util.ArrayList;

public class SymbolLibrary {
    private String parentDir;
    private ArrayList<Project> symbols;
    public SymbolLibrary (String dir){
        symbols = new ArrayList<>(20);
        parentDir = dir;
    }

    public void addSymbol(Project symbol){
        symbols.add(symbol);
    }

    public void removeSymbol(int index){
        symbols.remove(index);
    }
}
