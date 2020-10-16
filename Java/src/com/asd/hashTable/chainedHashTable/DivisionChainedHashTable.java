package com.asd.hashTable.chainedHashTable;

public class DivisionChainedHashTable extends ChainedHashTable {

    public DivisionChainedHashTable(int m) {
        super(m);
    }

    @Override
    public int hash(int k) {
        return (int)(k % m);
    }
}
