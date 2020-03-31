package com.example.learningjvm;

import java.nio.ByteBuffer;

public class HelloWorld {
    public static void main(String[] args) {
        ByteBuffer sb = ByteBuffer.allocateDirect(1024);

        System.out.println("hello world");
    }
}
