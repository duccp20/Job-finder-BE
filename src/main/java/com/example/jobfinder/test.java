package com.example.jobfinder;

import java.util.ArrayList;

public class test {

    private int i;

    public test(int i) {
        this.i = i;
    }

    public static void main(String[] args) {
        ArrayList<Object> arrayList = new ArrayList<>();

        test test = new test(1);

        arrayList.add(1);
        arrayList.add("1");
        arrayList.add(test);
        arrayList.add(null);

        for (Object o : arrayList) {
            System.out.println(o);
        }
    }
}
