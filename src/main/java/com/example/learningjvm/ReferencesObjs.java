package com.example.learningjvm;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferencesObjs {
    static class M2Object {
        byte[] m2 = new byte[2 * 1024 * 1024];
    }

    public static void main(String[] args) throws Exception {
        M2Object o = new M2Object();
        SoftReference<M2Object> s = new SoftReference<ReferencesObjs.M2Object>(o);
        o = null;
        System.gc();
        Thread.sleep(100);
        System.out.println(s.get() == null);

        o = new M2Object();
        WeakReference<M2Object> m = new WeakReference<ReferencesObjs.M2Object>(o);
        o = null;
        System.gc();
        Thread.sleep(100);
        System.out.println(m.get() == null);
        System.out.println(s.get() == null);
    }
}
