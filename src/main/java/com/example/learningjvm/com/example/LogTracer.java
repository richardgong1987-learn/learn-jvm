package com.example.learningjvm.com.example;

public class LogTracer extends Tracer {
    public LogTracer(String title) {
        super(title);
    }

    public LogTracer() {
        super();
    }

    public void set(Object log) {
        command("set", new Object[]{log});
    }

    public void print(Object message) {
        command("print", new Object[]{message});
    }

    public void println(Object message) {
        command("println", new Object[]{message});
    }

    public void printf(String format, Object... args) {
        Object[] traceArgs = new Object[args.length + 1];
        traceArgs[0] = format;
        System.arraycopy(args, 0, traceArgs, 1, args.length);
        command("printf", traceArgs);
    }
}
