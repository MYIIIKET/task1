package com.mylllket_inc.app;

import junit.framework.TestCase;


public class IntSetTest extends TestCase {

    public void testIntSet() throws Exception {
        IntSet a = new IntSet();
        a.add(319);
        a.add(0);
        a.add(63);
        a.add(513);
        a.add(54414);
        a.add(54411);
        a.add(143);
//        a.add(64);
        a.printIntSet();
    }
}