package com.example.learningjvm;

public class SynchronizedDemo {

    public static void main(String[] args) {
        Object o = new Object();
        synchronized (o) {
            System.out.println("ok");
        }
    }
}
