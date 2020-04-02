package com.example.learningjvm;

public class Block {
    static String s = "s";
    static {
        s = "ss";
    }

    static {
        s = "aaaa";
    }

    private String a = "s1";

    {
        b = 20;
    }

    private int b = 10;

    {
        a = "s2";
    }

    public Block() {

    }

    public Block(String a, int b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {
        Block d = new Block("s3", 30);
        System.out.println(d.a);
        System.out.println(d.b);
    }
}
