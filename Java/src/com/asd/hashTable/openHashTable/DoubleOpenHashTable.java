package com.asd.hashTable.openHashTable;

public class DoubleOpenHashTable extends OpenHashTabel{

    public DoubleOpenHashTable(int m) {
        super(m);
    }

    @Override
    protected int hash(int key, int i) {
        return (hash1(key) + i * hash2(key)) % m;
    }

    private int hash1(int key){
        return key % m;
    }

    private int hash2(int key){
        return 1 + (key % m-1);
    }
}
