package com.asd.utilities;

public interface ITable {
    boolean insert(Element element);
    Element search(int key);
    boolean delete(Element element);
}
