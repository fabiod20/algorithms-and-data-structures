package com.asd.hashTable.openHashTable;

public class LinearOpenHashTable extends OpenHashTabel {

    public LinearOpenHashTable(int m) {
        super(m);
    }

    @Override
    protected int hash(int key, int i) {
        return (hash1(key) + i) % m;
    }

    private int hash1(int key){
        return key % m;
    }}
