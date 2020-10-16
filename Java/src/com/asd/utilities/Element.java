package com.asd.utilities;

public class Element implements IElement {

    private int key;

    public Element(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Element))
            return false;
        if (obj == this)
            return true;
        return key == ((Element) obj).key;
    }

    @Override
    public int hashCode() {
        return key;
    }
}
