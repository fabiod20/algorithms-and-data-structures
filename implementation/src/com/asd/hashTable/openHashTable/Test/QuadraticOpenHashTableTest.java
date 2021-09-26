package com.asd.hashTable.openHashTable.Test;

import com.asd.utilities.Element;
import com.asd.hashTable.openHashTable.QuadraticOpenHashTable;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

class QuadraticOpenHashTableTest {

    private static final int TEST_M = 16;
    private static final int TEST_ITERATION = 10;
    private static QuadraticOpenHashTable testTable ;

    @BeforeEach
    void init() {
        testTable = new QuadraticOpenHashTable(TEST_M);

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
    void insertTime() {
        int M = (int) Math.pow(10, 6);
        int K = (int) Math.pow(10, 9);
        int OP_ITERATION = 10;
        final int N_STEP = 1000;

        FileWriter insertFileWriter;
        PrintWriter insertPrintWriter;

        try {
            insertFileWriter = new FileWriter("HashTable/Open/Quadratic/Insert.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            QuadraticOpenHashTable quadraticOpenHashTable = new QuadraticOpenHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            int n = 0;
            while(n < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(K);

                if(quadraticOpenHashTable.search(randNumber) == null) {
                    Element e = new Element(randNumber);

                    for(int i = 0; i < OP_ITERATION; i++) {
                        startTime = System.nanoTime();
                        result = quadraticOpenHashTable.insert(e);
                        endTime = System.nanoTime();
                        estimatedTime = endTime - startTime;

                        quadraticOpenHashTable.deleteNull(e);

                        insertPrintWriter.println(quadraticOpenHashTable.getM() + "\t" + quadraticOpenHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                    }

                    for(int i = 0; i < N_STEP; i ++) {
                        do {
                            random.setSeed(System.nanoTime());
                            randNumber = random.nextInt(K);
                            result = quadraticOpenHashTable.insert(new Element(randNumber));
                        } while(!result);
                    }

                    n = n + N_STEP;
                }
            }

            insertFileWriter.close();
            insertPrintWriter.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchAverageTime() {
        int M = (int) Math.pow(10, 6);
        int K = (int) Math.pow(10, 9);
        int OP_ITERATION = 10;
        final int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Open/Quadratic/SearchAverage.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            QuadraticOpenHashTable quadraticOpenHashTable = new QuadraticOpenHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < M; n = n + N_STEP) {
                Element e_true = null;

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(K);
                        e_true = new Element(randNumber);
                        result = quadraticOpenHashTable.insert(e_true);
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                if(random.nextBoolean())
                    randNumber = random.nextInt(K);     // ricerca random
                else
                    randNumber = e_true.getKey();       // ricerca vera

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = quadraticOpenHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(quadraticOpenHashTable.getM() + "\t" + quadraticOpenHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void insertCollision() {
        int M = (int) Math.pow(10, 4);
        int K = (int) Math.pow(10, 8);

        FileWriter insertFileWriter;
        PrintWriter insertPrintWriter;

        try {
            insertFileWriter = new FileWriter("HashTable/Open/Quadratic/InsertCollision.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "collisions");

            QuadraticOpenHashTable quadraticOpenHashTable = new QuadraticOpenHashTable(M);
            Random random = new Random();
            int randNumber;

            while(quadraticOpenHashTable.getN() < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(K);

                Element e = new Element(randNumber);
                quadraticOpenHashTable.insertCollision(e, insertPrintWriter);
            }

            insertPrintWriter.close();
            insertFileWriter.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}