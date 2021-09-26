package com.asd.hashTable.openHashTable.Test;

import com.asd.utilities.Element;
import com.asd.hashTable.openHashTable.LinearOpenHashTable;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

class LinearOpenHashTableTest {

    private static final int TEST_M = 10;
    private static final int TEST_ITERATION = 10;
    private static LinearOpenHashTable testTable ;

    @BeforeEach
    void init() {
        testTable = new LinearOpenHashTable(TEST_M);

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
            insertFileWriter = new FileWriter("HashTable/Open/Linear/Insert.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            LinearOpenHashTable linearOpenHashTable = new LinearOpenHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            int n = 0;
            while(n < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(K);

                if(linearOpenHashTable.search(randNumber) == null) {
                    Element e = new Element(randNumber);

                    for(int i = 0; i < OP_ITERATION; i++) {
                        startTime = System.nanoTime();
                        result = linearOpenHashTable.insert(e);
                        endTime = System.nanoTime();
                        estimatedTime = endTime - startTime;

                        linearOpenHashTable.deleteNull(e);

                        insertPrintWriter.println(linearOpenHashTable.getM() + "\t" + linearOpenHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                    }

                    for(int i = 0; i < N_STEP; i ++) {
                        do {
                            random.setSeed(System.nanoTime());
                            randNumber = random.nextInt(K);
                            result = linearOpenHashTable.insert(new Element(randNumber));
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
        int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Open/Linear/SearchAverage.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            LinearOpenHashTable linearOpenHashTable = new LinearOpenHashTable(M);
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
                        result = linearOpenHashTable.insert(e_true);
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                if(random.nextBoolean())
                    randNumber = random.nextInt(K);     // ricerca random
                else
                    randNumber = e_true.getKey();       // ricerca vera

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = linearOpenHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(linearOpenHashTable.getM() + "\t" + linearOpenHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchWorstTime() {
        int M = (int) Math.pow(10, 6);
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Open/Linear/SearchWorst.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            LinearOpenHashTable linearOpenHashTable = new LinearOpenHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < M; n = n + N_STEP) {

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(M) * (M / 1000);
                        result = linearOpenHashTable.insert(new Element(randNumber));
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(M) * (M / 1000);

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = linearOpenHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(linearOpenHashTable.getM() + "\t" + linearOpenHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchDeleted() {
        int M = (int) Math.pow(10, 6);
        int K = (int) Math.pow(10, 9);
        int OP_ITERATION = 10;
        int N_STEP = 1000;
        int D = 10*N_STEP;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Open/Linear/SearchDeleted.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n"  + '\t' + "d" + '\t' + "result" + '\t' + "time");

            LinearOpenHashTable linearOpenHashTable = new LinearOpenHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            Vector<Element> deletedElement = new Vector<>(D);
            Element e_true = null;
            Element e_deleted = null;

            for(int n = 0; n < M - D; n = n + N_STEP) {

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(K);
                        e_true = new Element(randNumber);
                        result = linearOpenHashTable.insert(e_true);
                    } while(!result);
                }

                for(int i = 0; i < D; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(K);
                        e_deleted = new Element(randNumber);
                        result = linearOpenHashTable.insert(e_deleted);

                    } while(!result);
                    deletedElement.add(e_deleted);
                }

                for(int i = 0; i < D; i ++) {
                    linearOpenHashTable.delete(deletedElement.get(i));
                }
                deletedElement.removeAllElements();

                random.setSeed(System.nanoTime());
                if(random.nextBoolean())
                    randNumber = random.nextInt(K);     // ricerca random
                else
                    randNumber = e_true.getKey();       // ricerca vera

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = linearOpenHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(linearOpenHashTable.getM() + "\t" + linearOpenHashTable.getN() + "\t" + linearOpenHashTable.getD() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteTime() {
        int M = (int) Math.pow(10, 6);
        int K = (int) Math.pow(10, 6);
        int OP_ITERATION = 10;
        final int N_STEP = 1000;

        FileWriter deleteFileWriter;
        PrintWriter deletePrintWriter;

        try {
            deleteFileWriter = new FileWriter("HashTable/Open/Linear/Delete.csv", true);
            deletePrintWriter = new PrintWriter(deleteFileWriter);
            deletePrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            LinearOpenHashTable linearOpenHashTable = new LinearOpenHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < M; n = n + N_STEP) {
                Element e;
                do {
                    random.setSeed(System.nanoTime());
                    randNumber = random.nextInt(K);
                    e = new Element(randNumber);

                    result = linearOpenHashTable.insert(e);
                } while(!result);

                for (int i = 0; i < OP_ITERATION; i++) { // va bene fare più cancellazioni dello stesso elemento, per ogni n?
                    startTime = System.nanoTime();
                    result = linearOpenHashTable.delete(e);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    deletePrintWriter.println(linearOpenHashTable.getM() + "\t" + (linearOpenHashTable.getN() + 1) + "\t" + result + '\t' + estimatedTime);

                    linearOpenHashTable.insert(e);
                }

                linearOpenHashTable.delete(e);

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(K);
                        result = linearOpenHashTable.insert(new Element(randNumber));
                    } while(!result);
                }
            }

            deleteFileWriter.close();
            deletePrintWriter.close();

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
            insertFileWriter = new FileWriter("HashTable/Open/Linear/InsertCollision.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "collisions");

            LinearOpenHashTable linearOpenHashTable = new LinearOpenHashTable(M);
            Random random = new Random();
            int randNumber;

            while(linearOpenHashTable.getN() < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(K);

                Element e = new Element(randNumber);
                linearOpenHashTable.insertCollision(e, insertPrintWriter);
            }

            insertPrintWriter.close();
            insertFileWriter.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}