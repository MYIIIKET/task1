package com.mylllket_inc.app;

import junit.framework.TestCase;

public class SetInt64Test extends TestCase {
    public void testIsSubsetOf() throws Exception {
        final SetInt64 a = new SetInt64();
        final SetInt64 b = new SetInt64();
        //10110101
        b.add(0);
        b.add(2);
        b.add(4);
        b.add(5);
        b.add(7);

        //1011
        a.add(0);
        a.add(1);
        a.add(3);
        assertTrue(a.isSubsetOf(b));

        //1111
        a.add(0);
        a.add(1);
        a.add(2);
        a.add(3);
        assertFalse(a.isSubsetOf(b));

    }

    public void testDifference() throws Exception {
        SetInt64 a = new SetInt64();
        final SetInt64 b = new SetInt64();
        a.add(5);
        b.add(7);
        a=a.difference(b);
        assertEquals(96,a.getVal());
    }

    public void testIntersection() throws Exception {
        SetInt64 a = new SetInt64();
        final SetInt64 b = new SetInt64();
        a.add(0);
        a.add(2);
        b.add(3);
        a=a.intersection(b);
        assertEquals(0, a.getVal());
    }

    public void testUnion() throws Exception {
        SetInt64 a = new SetInt64();
        final SetInt64 b = new SetInt64();
        a.add(5);
        b.add(8);
        a=a.union(b);
        assertEquals(288, a.getVal());
    }

    public void testAdd() throws Exception {
        final SetInt64 test = new SetInt64();

        assertFalse(test.contains(-1));
        test.add(-1);
        assertFalse(test.contains(-1));

        assertFalse(test.contains(0));
        test.add(0);
        assertTrue(test.contains(0));

        assertFalse(test.contains(31));
        test.add(31);
        assertTrue(test.contains(31));

        assertFalse(test.contains(62));
        test.add(62);
        assertTrue(test.contains(62));

        assertFalse(test.contains(63));
        test.add(63);
        assertFalse(test.contains(63));
    }

    public void testRemove() throws Exception {
        final SetInt64 test = new SetInt64();
        assertFalse(test.contains(5));
        test.add(5);
        assertTrue(test.contains(5));
        test.remove(5);
        test.remove(6);
        assertFalse(test.contains(5));
    }


    public void testSetInt() throws Exception {
        testAdd();
        testRemove();
        testUnion();
        testIntersection();
        testDifference();
        testIsSubsetOf();
    }
}