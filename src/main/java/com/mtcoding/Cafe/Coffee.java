package com.mtcoding.Cafe;

public class Coffee {
    String name;

    public Coffee(String name) {
        this.name = name;
        printString();
    }
    public void printString() {
        System.out.println(name+" 완성!");
    }
}
