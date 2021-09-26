package com.asd.directTable.Test;

import com.asd.utilities.Element;
import com.asd.directTable.DirectTable;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

class DirectTableTest {

    private static final int TEST_M = 10;
    private static final int TEST_ITERATION = 10;
    private static DirectTable testTable;

    @BeforeEach
    void init() {
        testTable = new DirectTable(TEST_M);

        for (int i = 0; i < TEST_M / 2; i++) {
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
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter insertFileWriter;
        PrintWriter insertPrintWriter;

        try {
            insertFileWriter = new FileWriter("DirectTable/Insert.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DirectTable directTable = new DirectTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            int n = 0;
            while(n < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(M);

                if(directTable.search(randNumber) == null) {
                    Element e = new Element(randNumber);

                    for(int i = 0; i < OP_ITERATION; i++) { // va bene fare più inserimenti dello stesso elemento, per ogni n?
                        startTime = System.nanoTime();
                        result = directTable.insert(e);
                        endTime = System.nanoTime();
                        estimatedTime = endTime - startTime;

                        directTable.delete(e);

                        insertPrintWriter.println(directTable.getM() + "\t" + directTable.getN() + "\t" + result + '\t' + estimatedTime);
                    }

                    for(int i = 0; i < N_STEP; i ++) {
                        do {
                            random.setSeed(System.nanoTime());
                            randNumber = random.nextInt(M);
                            result = directTable.insert(new Element(randNumber));
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
    void searchTime() {
        int M = (int) Math.pow(10, 6);
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("DirectTable/Search.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DirectTable directTable = new DirectTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < M; n = n + N_STEP) {

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(M);
                        result = directTable.insert(new Element(randNumber));
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(M);

                for (int i = 0; i < OP_ITERATION; i++) { // va bene fare più ricerche dello stesso elemento, per ogni n?
                    startTime = System.nanoTime();
                    Element e = directTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(directTable.getM() + "\t" + directTable.getN() + "\t" + result + '\t' + estimatedTime);
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
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter deleteFileWriter;
        PrintWriter deletePrintWriter;

        try {
            deleteFileWriter = new FileWriter("DirectTable/Delete.csv", true);
            deletePrintWriter = new PrintWriter(deleteFileWriter);
            deletePrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DirectTable directTable = new DirectTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < M; n = n + N_STEP) {
                Element e;
                do {
                    random.setSeed(System.nanoTime());
                    randNumber = random.nextInt(M);
                    e = new Element(randNumber);
                    result = directTable.insert(e);
                } while(!result);

                for (int i = 0; i < OP_ITERATION; i++) { // va bene fare più cancellazioni dello stesso elemento, per ogni n?
                    startTime = System.nanoTime();
                    result = directTable.delete(e);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    deletePrintWriter.println(directTable.getM() + "\t" + (directTable.getN() + 1) + "\t" + result + '\t' + estimatedTime);

                    directTable.insert(e);
                }

                directTable.delete(e);

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(M);
                        result = directTable.insert(new Element(randNumber));
                    } while(!result);
                }
            }

            deleteFileWriter.close();
            deletePrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}