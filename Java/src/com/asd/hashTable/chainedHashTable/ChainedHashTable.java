package com.asd.hashTable.chainedHashTable;

import com.asd.utilities.Element;
import com.asd.utilities.ITable;

import java.util.LinkedList;

public abstract class ChainedHashTable implements ITable {

    private LinkedList<Element> [] table;

    protected int m;  // numero di liste
    private int n;  // numero di elementi

    public ChainedHashTable(int m) {
        this.m = m;
        n = 0;
        table = new LinkedList[m];

        for (int i = 0; i < m; i++) {
            table[i] = new LinkedList<Element>();
        }
    }

    @Override
    public boolean insert(Element element) {
        int i = hash(element.getKey());
        table[i].addFirst(element); // O(1)
        n = n + 1;
        return true;
    }

    @Override
    public Element search(int key) {
        Element e = new Element(key);
        int k = hash(key);

//      https://www.baeldung.com/java-collections-complexity
        int index = table[k].indexOf(e);    // O(n)

        if(index != -1) {
            return table[k].get(index); // O(n)
        } else
            return null;
    }

    @Override
    public boolean delete(Element element) {
        int i = hash((int) element.getKey());
        boolean result;
        result = table[i].remove(element);  // O(1)

        if(result) {
            n = n - 1;
            return true;
        } else
            return false;
    }

    protected abstract int hash(int key);

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
        for(int i = 0; i < m; i++) {
            if(table[i] != null) {
                for(int j = 0; j < table[i].size(); j++ ) {
                    System.out.print(table[i].get(j).getKey() + " ");
                }
                System.out.print('\n');
            }
        }
        System.out.println("----------");
    }
}
