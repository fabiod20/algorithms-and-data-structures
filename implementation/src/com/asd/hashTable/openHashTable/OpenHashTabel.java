package com.asd.hashTable.openHashTable;

import com.asd.utilities.Element;
import com.asd.utilities.ITable;

import java.io.PrintWriter;

public abstract class OpenHashTabel implements ITable {

    private Element[] table;
    private static final Element DELETED = new Element(-1);
    protected int m;  // numero di elementi
    private int n;  // numero di elementi effettivi
    private int d;  // numeri elementi DELETED

    public OpenHashTabel(int m) {
        this.m = m;
        n = 0;
        table = new Element[m];
    }

    @Override
    public boolean insert(Element element) {
        int i = 0;
        int j = 0;

        do {
            j = hash(element.getKey(), i);

            if(element.equals(table[j])) {  // garantisce che le chiavi siano distinte (non modifica la complessità)
                return false;
            }

            if(table[j] == null || DELETED.equals(table[j])) {
                if(DELETED.equals(table[j])) {
                    d = d - 1;
                }

                table[j] = element;
                n = n + 1;
                return true;
            } else {
              i = i + 1;
            }
        } while(i < m);
        return false;
    }

    public boolean insertCollision(Element element, PrintWriter insertPrintWriter) {

        int i = 0;
        int j = 0;

        do {
            j = hash(element.getKey(), i);

            if(element.equals(table[j])) {
                return false;
            }

            if(table[j] == null || DELETED.equals(table[j])) {
                if(DELETED.equals(table[j])) {
                    d = d - 1;
                }
                insertPrintWriter.println(this.m + "\t" + this.n + "\t" + i);
                table[j] = element;
                n = n + 1;
                return true;
            } else {
                i = i + 1;
            }
        } while(i < m);
        return false;
    }

    @Override
    public Element search(int key) {
        int i = 0;
        int j = 0;

        do {
            j = hash(key, i);

            if(table[j] != null && table[j].getKey() == key) {
                return table[j];
            } else {
                i = i + 1;
            }
        } while(i < m && table[j] != null);
        return null;
    }

    @Override
    public boolean delete(Element element) {
        int i = 0;
        int j = 0;

        do {
            j = hash(element.getKey(), i);

            if(table[j] != null && table[j].equals(element)) {
                table[j] = DELETED;
                d = d + 1;
                n = n - 1;
                return true;
            } else {
                i = i + 1;
            }
        } while(i < m && table[j] != null);
        return false;
    }

    public boolean deleteNull(Element element) {
        int i = 0;
        int j = 0;

        do {
            j = hash(element.getKey(), i);

            if(table[j] != null && table[j].equals(element)) {
                table[j] = null;
                n = n - 1;
                return true;
            } else {
                i = i + 1;
            }
        } while(i < m && table[j] != null);
        return false;
    }

    protected abstract int hash(int key, int i);

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int getD() {
        return d;
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
