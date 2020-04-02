package com.example.learningjvm;

public class A {
    public static void main(String[] args) {
        int a = 10;
        int b = a++ + ++a + a--;
        int c = 10 + 12 + 12;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c==b);
    }
}
