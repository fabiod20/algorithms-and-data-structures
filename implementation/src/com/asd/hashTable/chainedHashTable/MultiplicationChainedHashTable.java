package com.asd.hashTable.chainedHashTable;

public class MultiplicationChainedHashTable extends ChainedHashTable {

    public MultiplicationChainedHashTable(int m) {
        super(m);
    }

    @Override
    public int hash(int k) {
        double A = ((Math.sqrt(5) - 1) / 2);
        return (int) Math.floor(m * ((k * A) % 1));
    }
}
