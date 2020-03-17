package ru.croc.java.school.lecture3;

import java.util.ArrayList;
import java.util.List;

public class Array {
    private List<String> list = new ArrayList<String>();

    public void add(String value) {
        list.add(value);
    }

    public void remove(String value) {
        list.remove(value);
    }

    public String[] values() {
        return list.toArray(new String[list.size()]);
    }
}
