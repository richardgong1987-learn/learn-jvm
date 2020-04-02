package com.example.learningjvm.com.example;

public class MultiCatch {
    public static void main(String[] args) {
        System.out.println(test2());
    }

    public static String testfs() {
        try {
            String aa = "";
            throw new Exception("aaa");
        } catch (Exception e) {
            throw new Exception("bbb");
        } finally {
            return "c";
        }
    }

    public static int test2() {
        int a = 0;
        try {
            a = 10;
            return a;
        } finally {
            a = 30;
        }

    }
}
