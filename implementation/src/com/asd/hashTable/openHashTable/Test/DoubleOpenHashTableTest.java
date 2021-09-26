package com.asd.hashTable.openHashTable.Test;

import com.asd.utilities.Element;
import com.asd.hashTable.openHashTable.DoubleOpenHashTable;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

class DoubleOpenHashTableTest {

    private static final int TEST_M = 16;
    private static final int TEST_ITERATION = 10;
    private static DoubleOpenHashTable testTable ;

    @BeforeEach
    void init() {
        testTable = new DoubleOpenHashTable(TEST_M);

        for(int i = 0; i < TEST_M / 2; i ++) {
            Element e = new Element(i);
            testTable.insert(e);
        }

        System.out.println("Test table");
        testTable.printTable();
    }

    @RepeatedTest(TEST_ITERATION)
    void insert() {
        Random random = new Random();
        int randNumber;
        Element e;
        boolean result;

        random.setSeed(System.nanoTime());
        randNumber = random.nextInt(TEST_M / 2);
        e = new Element(randNumber);
        result = testTable.insert(e);
        Assert.assertFalse(result);

        random.setSeed(System.nanoTime());
        randNumber = random.nextInt(TEST_M / 2);
        e = new Element(TEST_M / 2 + randNumber);
        result = testTable.insert(e);
        Assert.assertTrue(result);
    }

    @RepeatedTest(TEST_ITERATION)
    void search() {
        Random random = new Random();
        int randNumber;
        Element e;

        random.setSeed(System.nanoTime());
        randNumber = random.nextInt(TEST_M / 2);
        e = testTable.search(randNumber);
        Assert.assertNotNull(e);

        random.setSeed(System.nanoTime());
        randNumber = random.nextInt(TEST_M / 2);
        e = testTable.search(TEST_M / 2 + randNumber);
        Assert.assertNull(e);
    }

    @RepeatedTest(TEST_ITERATION)
    void delete() {
        Random random = new Random();
        int randNumber;
        Element e;
        boolean result;

        random.setSeed(System.nanoTime());
        randNumber = random.nextInt(TEST_M / 2);
        e = new Element(randNumber);
        result = testTable.delete(e);
        Assert.assertTrue(result);

        random.setSeed(System.nanoTime());
        randNumber = random.nextInt(TEST_M / 2);
        e = new Element(TEST_M / 2 + randNumber);
        result = testTable.delete(e);
        Assert.assertFalse(result);
    }

    @Test
    void insertCollision() {
        int M = (int) Math.pow(10, 4);
        int K = (int) Math.pow(10, 8);

        FileWriter insertFileWriter;
        PrintWriter insertPrintWriter;

        try {
            insertFileWriter = new FileWriter("HashTable/Open/Double/InsertCollision.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "collisions");

            DoubleOpenHashTable doubleOpenHashTable = new DoubleOpenHashTable(M);
            Random random = new Random();
            int randNumber;

            while(doubleOpenHashTable.getN() < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(K);

                Element e = new Element(randNumber);
                doubleOpenHashTable.insertCollision(e, insertPrintWriter);
            }

            insertPrintWriter.close();
            insertFileWriter.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}