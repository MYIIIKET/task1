package com.mylllket_inc.app;

import junit.framework.TestCase;

import java.util.BitSet;


public class IntSetTest extends TestCase {

    /**
     * Create {@link IntSet#IntSet(int)} using size
     * Cerate {@link IntSet#IntSet(int[])} using int data
     */
    public void testCreationIntSet() {
        int size = 3;
        IntSet a = new IntSet(size);
        assertEquals("Wrong size. Expected " + size + " actual " + a.getSize(), size, a.getSize());

        int[] data = {3, 63, 512, 4884};
        IntSet b = new IntSet(data);
        assertEquals("Wrong size. Expected " + data.length + " actual " + a.getSize(), size, a.getSize());
    }

    /**
     * Apply {@link IntSet#add(int)} to {@link IntSet#IntSet(int)} or{@link IntSet#IntSet(int)}
     * Applying number more than size*64 adds it to the (size+1)*64
     * <p>
     * Current size==1
     * After adding size==2
     */
    public void testAddingIntSet() {
        int size = 1;
        int value = 64;
        IntSet a = new IntSet(size);
        a.add(value);
        assertEquals("Wrong size. Expected " + 2 + " actual " + a.getSize(), 2, a.getSize());
    }

    /**
     * Apply {@link IntSet#remove(int)}
     * After removing checks on defrag
     */
    public void testRemovingIntSet() {
        int size = 1;
        int value = 64;
        IntSet a = new IntSet(size);

        a.add(value);

        a.remove(value);
        a.printIntSet();
        assertEquals("Wrong size. Expected " + 1 + " actual " + a.getSize(), 1, a.getSize());

    }


    public void testIntSet() throws Exception {
        IntSet a = new IntSet(1);
        IntSet b = new IntSet(1);
        int[] data = {-64, 24, 2245, 211, 97655464, 112};
        IntSet c = new IntSet(data);
        c.printIntSet();
    }
}