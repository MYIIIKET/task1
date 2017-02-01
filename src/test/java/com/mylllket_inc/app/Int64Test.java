package com.mylllket_inc.app;

import junit.framework.TestCase;

public class Int64Test extends TestCase {
    public void testIsSubsetOf() throws Exception {
        final Int64 a = new Int64();
        final Int64 b = new Int64();
        final Int64 c = new Int64();
        //b=10110101
        b.add(0);
        b.add(2);
        b.add(4);
        b.add(5);
        b.add(7);

        //a=1011
        a.add(0);
        a.add(1);
        a.add(3);
        assertTrue(a.isSubsetOf(b));

        //a=1111
        a.add(0);
        a.add(1);
        a.add(2);
        a.add(3);
        assertFalse(a.isSubsetOf(b));

        //c=1101
        c.add(0);
        c.add(2);
        c.add(3);
        assertTrue(c.isSubsetOf(b));


    }

    public void testDifference() throws Exception {
        final Int64 a = new Int64();
        final Int64 b = new Int64();
        a.add(5);
        b.add(7);
        assertFalse(a.difference(b));
        assertFalse(b.difference(a));
        a.add(7);
        b.add(5);
        assertTrue(a.difference(b));
        assertTrue(b.difference(a));
    }

    public void testIntersection() throws Exception {
        Int64 a = new Int64();
        final Int64 b = new Int64();
        a.add(0);
        a.add(2);
        b.add(3);
        a=a.intersection(b);
        assertEquals(0, a.getVal());
    }

    public void testUnion() throws Exception {
        Int64 a = new Int64();
        final Int64 b = new Int64();
        a.add(5);
        b.add(8);
        a=a.union(b);
        assertEquals(288, a.getVal());
    }

    public void testAdd() throws Exception {
        final Int64 test = new Int64();

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
        assertTrue(test.contains(63));

        assertFalse(test.contains(64));
        test.add(64);
        assertFalse(test.contains(64));
    }

    public void testRemove() throws Exception {
        final Int64 test = new Int64();
        assertFalse(test.contains(5));
        test.add(5);
        assertTrue(test.contains(5));
        test.remove(5);
        test.remove(6);
        assertFalse(test.contains(5));
    }


    public void testIntSet() throws Exception {
        testAdd();
        testRemove();
        testUnion();
        testIntersection();
        testDifference();
        testIsSubsetOf();
    }
}