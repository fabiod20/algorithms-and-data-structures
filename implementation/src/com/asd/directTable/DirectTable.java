package com.asd.directTable;

import com.asd.utilities.Element;
import com.asd.utilities.ITable;

public class DirectTable implements ITable {

    private Element[] table;
    private int m;  // numero di elementi
    private int n;  // numero di elementi effettivi

    public DirectTable(int m) {
        this.m = m;
        n = 0;
        table = new Element[m];
    }

    @Override
    public boolean insert(Element element) {
        if(table[element.getKey()] == null){
            table[element.getKey()] = element;
            n = n + 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Element search(int key) {
        return table[key];
    }

    @Override
    public boolean delete(Element element) {
        if(table[element.getKey()] != null){
            table[element.getKey()] = null;
            n = n - 1;
            return true;
        } else {
            return false;
        }
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public void printTable() {
        System.out.println("----------");
        System.out.println("m = " + m);
        System.out.println("n = " + n);
        for(int i = 0; i < m; i++){
            if(table[i] != null){
                System.out.println(table[i].getKey());
            }
        }
        System.out.println("----------");
    }
}
