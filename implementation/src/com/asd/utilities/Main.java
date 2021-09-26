package com.asd.utilities;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        // write your code here

        LinkedList<Element> list = new LinkedList<>();

        Element e1 = new Element(0);
        Element e2 = new Element(0);

        list.add(e1);

        if(list.contains(e2))
            System.out.println("ciao");
    }
}
