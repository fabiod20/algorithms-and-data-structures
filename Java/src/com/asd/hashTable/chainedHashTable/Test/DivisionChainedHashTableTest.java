package com.asd.hashTable.chainedHashTable.Test;

import com.asd.utilities.Element;
import com.asd.hashTable.chainedHashTable.DivisionChainedHashTable;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

class DivisionChainedHashTableTest {

    private static final int TEST_M = 10;
    private static final int TEST_ITERATION = 10;
    private static DivisionChainedHashTable testTable ;

    @BeforeEach
    void init() {
        testTable = new DivisionChainedHashTable(TEST_M);

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
        randNumber = random.nextInt(TEST_M);
        e = new Element(randNumber);
        result = testTable.insert(e);
        Assert.assertTrue(result);

        System.out.println("Test table");
        testTable.printTable();
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
            insertFileWriter = new FileWriter("HashTable/Chained/Division/Insert.csv", true);
            insertPrintWriter = new PrintWriter(insertFileWriter);
            insertPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            int n = 0;
            while(n < M) {
                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(M);

                if(divisionChainedHashTable.search(randNumber) == null) {
                    Element e = new Element(randNumber);

                    for(int i = 0; i < OP_ITERATION; i++) {
                        startTime = System.nanoTime();
                        result = divisionChainedHashTable.insert(e);
                        endTime = System.nanoTime();
                        estimatedTime = endTime - startTime;

                        divisionChainedHashTable.delete(e);

                        insertPrintWriter.println(divisionChainedHashTable.getM() + "\t" + divisionChainedHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                    }


                    for(int i = 0; i < N_STEP; i ++) {
                        do {
                            random.setSeed(System.nanoTime());
                            randNumber = random.nextInt(M);
                            result = divisionChainedHashTable.insert(new Element(randNumber));
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
    void searchAverageTime1() {
        int M = (int) Math.pow(10, 6);
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Chained/Division/SearchAverage1.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;


            for (int n = 0; n < 2*M; n = n + N_STEP) {

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(M);
                        result = divisionChainedHashTable.insert(new Element(randNumber));
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(2*M);

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = divisionChainedHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(divisionChainedHashTable.getM() + "\t" + divisionChainedHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchAverageTimeA() {
        int M = (int) Math.pow(10, 3);
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Chained/Division/SearchAverageA.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < (int) Math.pow(M, 2); n = n + N_STEP) {

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt((int) Math.pow(M, 2));
                        result = divisionChainedHashTable.insert(new Element(randNumber));
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                randNumber = random.nextInt((int) Math.pow(M, 2));

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = divisionChainedHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(divisionChainedHashTable.getM() + "\t" + divisionChainedHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fare un analisi generando gli elementi con distribuzione gaussiana
    @Test
    void searchWorstTime() {
        int M = (int) Math.pow(10, 6);
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter searchFileWriter;
        PrintWriter searchPrintWriter;

        try {
            searchFileWriter = new FileWriter("HashTable/Chained/Division/SearchWorst.csv", true);
            searchPrintWriter = new PrintWriter(searchFileWriter);
            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
            Random random = new Random();
            int randNumber;
            long startTime, endTime, estimatedTime;
            boolean result;

            for(int n = 0; n < M; n = n + N_STEP) {

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(M) * (M / 1000);
                        result = divisionChainedHashTable.insert(new Element(randNumber));
                    } while(!result);
                }

                random.setSeed(System.nanoTime());
                randNumber = random.nextInt(M) * (M / 1000);

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    Element e = divisionChainedHashTable.search(randNumber);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    result = e != null;

                    searchPrintWriter.println(divisionChainedHashTable.getM() + "\t" + divisionChainedHashTable.getN() + "\t" + result + '\t' + estimatedTime);
                }
            }

            searchFileWriter.close();
            searchPrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void searchWorstTime() {
//        int M = (int) Math.pow(10, 3);
//        int OP_ITERATION = 20;
//        int N_STEP = 1000;
//
//        FileWriter searchFileWriter;
//        PrintWriter searchPrintWriter;
//
//        try {
//            searchFileWriter = new FileWriter("HashTable/Chained/Division/SearchWorst.csv", true);
//            searchPrintWriter = new PrintWriter(searchFileWriter);
//            searchPrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");
//
//            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
//            Random random = new Random();
//            int randNumber;
//            long startTime, endTime, estimatedTime;
//            boolean result;
//
//            int n = 0;
//            while (n < (int) Math.pow(M, 2)) {
//
//                int j = 0;
////                while(j < N_STEP) {
////                    divisionChainedHashTable.insert(new Element((j + n) * M));
////                    j = j + 1;
////                }
//                while(j < N_STEP) {
//                    random.setSeed(System.nanoTime());
//                    randNumber = random.nextInt((int) Math.pow(M, 2)) * M;
//
//                    if(divisionChainedHashTable.search(randNumber) == null) {
//                        divisionChainedHashTable.insert(new Element(randNumber));
//                        j = j + 1;
//                    }
//                }
//                n = n + N_STEP;
//
//                random.setSeed(System.nanoTime());
//                randNumber = random.nextInt((int) Math.pow(M, 2)) * M;
//
//                for (int i = 0; i < OP_ITERATION; i++) { // va bene fare più ricerche dello stesso elemento, per ogni n?
//                    startTime = System.nanoTime();
//                    Element e = divisionChainedHashTable.search(randNumber);
//                    endTime = System.nanoTime();
//                    estimatedTime = endTime - startTime;
//
//                    result = e != null;
//
//                    searchPrintWriter.println(divisionChainedHashTable.getM() + "\t" + divisionChainedHashTable.getN() + "\t" + result + '\t' + estimatedTime);
//                }
//            }
//
//            searchFileWriter.close();
//            searchPrintWriter.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void deleteTime() {
        int M = (int) Math.pow(10, 6);
        int OP_ITERATION = 10;
        int N_STEP = 1000;

        FileWriter deleteFileWriter;
        PrintWriter deletePrintWriter;

        try {
            deleteFileWriter = new FileWriter("HashTable/Chained/Division/Delete.csv", true);
            deletePrintWriter = new PrintWriter(deleteFileWriter);
            deletePrintWriter.println("m" + '\t' + "n" + '\t' + "result" + '\t' + "time");

            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
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

                    result = divisionChainedHashTable.insert(e);
                } while(!result);

                for (int i = 0; i < OP_ITERATION; i++) {
                    startTime = System.nanoTime();
                    result = divisionChainedHashTable.delete(e);
                    endTime = System.nanoTime();
                    estimatedTime = endTime - startTime;

                    deletePrintWriter.println(divisionChainedHashTable.getM() + "\t" + (divisionChainedHashTable.getN() + 1) + "\t" + result + '\t' + estimatedTime);

                    divisionChainedHashTable.insert(e);
                }

                divisionChainedHashTable.delete(e);

                for(int i = 0; i < N_STEP; i ++) {
                    do {
                        random.setSeed(System.nanoTime());
                        randNumber = random.nextInt(M) * (M / 1000);
                        result = divisionChainedHashTable.insert(new Element(randNumber));
                    } while(!result);
                }
            }

            deleteFileWriter.close();
            deletePrintWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void nOperationsTime() {
//        int M = (int) Math.pow(10, 6);
//        int N_OP =  (int) Math.pow(M, 2);
//        int N_STEP = 5000;
//        int MAX_INS = 2 * M;
//
//        FileWriter fileWriter;
//        PrintWriter printWriter;
//
//        try {
//            fileWriter = new FileWriter("HashTable/Chained/Division/nOperations.csv", true);
//            printWriter = new PrintWriter(fileWriter);
//            printWriter.println("n_op" + "time");
//
//            for(int n = 0; n < N_OP; n = n + N_STEP) {
//                DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
//
//                Random random = new Random();
//                int randNumber;
//                long startTime, endTime, estimatedTime;
//
//                for(int i = 0; i < n; i++) {
//
//
//                }
//            }
//
//
//
//
//
//            DivisionChainedHashTable divisionChainedHashTable = new DivisionChainedHashTable(M);
//            Random random = new Random();
//            int randNumber;
//            long startTime, endTime, estimatedTime;
//            boolean result;
//
//            int n = 0;
//            while (n < M) {
//                Element e;
//                do {
//                    random.setSeed(System.nanoTime());
//                    randNumber = random.nextInt(M);
//                    e = new Element(randNumber);
//
//                    result = divisionChainedHashTable.insert(e);
//                } while(!result);
//
//                for (int i = 0; i < OP_ITERATION; i++) { // va bene fare più cancellazioni dello stesso elemento, per ogni n?
//                    startTime = System.nanoTime();
//                    result = divisionChainedHashTable.delete(e);
//                    endTime = System.nanoTime();
//                    estimatedTime = endTime - startTime;
//
//                    deletePrintWriter.println(divisionChainedHashTable.getM() + "\t" + (divisionChainedHashTable.getN() + 1) + "\t" + result + '\t' + estimatedTime);
//
//                    divisionChainedHashTable.insert(e);
//                }
//
//                divisionChainedHashTable.delete(e);
//
//                int j = 0;
//                while(j < N_STEP) {
//                    random.setSeed(System.nanoTime());
//                    randNumber = random.nextInt(M);
//
//                    if(divisionChainedHashTable.search(randNumber) == null) {
//                        divisionChainedHashTable.insert(new Element(randNumber));
//                        j = j + 1;
//                    }
//                }
//                n = n + N_STEP;
//            }
//
//            fileWriter.close();
//            printWriter.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}