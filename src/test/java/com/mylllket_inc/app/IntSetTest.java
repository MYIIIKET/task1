package com.mylllket_inc.app;

import junit.framework.TestCase;


public class IntSetTest extends TestCase {

    public void testIntSet() throws Exception {
        IntSet a = new IntSet(1);
        IntSet b = new IntSet(1);


        a.add(3);
        a.add(2);
        a.add(1);
        a.printIntSet();
        b.add(3);
        b.add(1);
        b.add(65);
        b.printIntSet();
        a=a.minus(b);
        a.printIntSet();


    }
}