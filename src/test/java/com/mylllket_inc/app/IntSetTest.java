package com.mylllket_inc.app;

import junit.framework.TestCase;

import java.util.BitSet;


public class IntSetTest extends TestCase {

    public void testIntSet() throws Exception {
        IntSet a = new IntSet(1);
        IntSet b = new IntSet(1);
        int[] data={-64, 24, 2245, 211, 97655464, 112};
        IntSet c = new IntSet(data);
        c.printIntSet();
    }
}