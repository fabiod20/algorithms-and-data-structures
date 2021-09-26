package com.asd.hashTable.openHashTable;

public class QuadraticOpenHashTable extends OpenHashTabel {

    private static final int C1 = 1;
    private static final int C2 = 3;

    public QuadraticOpenHashTable(int m) {
        super(m);
    }

    @Override
    protected int hash(int key, int i) {
        return (hash1(key) + C1 * i + C2 * i^2) % m;
    }

    private int hash1(int key){
        return key % m;
    }
}
