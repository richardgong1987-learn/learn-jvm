package com.example.learningjvm.com.example;

public class Demo3_10 {
    public static void test(Animal animal) {
        animal.eat();
    }

    public static void main(String[] args) {

    }
}

abstract class Animal {
     void eat() { }
}
