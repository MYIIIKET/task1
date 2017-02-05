package com.mylllket_inc.app;

/**
 * Class containing {@link Int64} objects
 */

public class IntSet {
    /**
     * Array of {@link Int64}
     */
    private Int64[] arrData;
    /**
     * Size of {@link Int64} array
     */
    private int size = 0;


    private final static int baseSize = 63;
    /**
     * Number of bits in one {@link Int64} element
     */
    private final static int elemNum = 64;
    /**
     * Current size of allocated memory
     */
    private int curretSize = 0;

    /**
     * Number increasing after creating new {@link #IntSet(int)} or {@link #IntSet(int[])} object
     */
    private static byte globalID = 0;
    /**
     * Number of the current {@link IntSet} object
     */
    private int ID = 0;

    /**
     * Creates new array of {@link Int64} objects with specified <code>size</code>
     *
     * @param size Size of new {@link IntSet} object
     */
    public IntSet(int size) {
        if (size != 0) {
            globalID++;
            this.ID = globalID;
            arrData = new Int64[size];
            for (int i = 0; i < size; i++) {
                arrData[i] = new Int64();
            }
            this.size = size;
            curretSize = elemNum * size - 1;
        }
    }

    /**
     * Creates new array of {@link Int64} objects with specified <code>data</code>
     *
     * @param data Input array of <code>int</code>
     */
    public IntSet(int[] data) {
        this(data.length);
        for (int i = 0; i < data.length; i++) {
            this.add(data[i]);
        }
        IntSet tmp = checkOnDefrag(this);
        this.arrData = tmp.arrData;
    }

    /**
     * Add bit at field with number <code>val</code>
     * It increase the {@link #size} of the array if value is bigger than {@link #curretSize}
     *
     * @param val Number of bit to set
     */
    public void add(int val) {
        if (val < 0) return;
        if (val > curretSize) {
            curretSize += elemNum;
            size++;

            globalID--;
            IntSet newSet = new IntSet(size);
            System.arraycopy(this.arrData, 0, newSet.arrData, 0, this.arrData.length);
            this.arrData = newSet.arrData;
        }
        arrData[getIndex(val)].add(val % elemNum);
    }

    /**
     * Check on containing bit at specified <code>val</code>
     *
     * @param val Number of bit to check
     * @return true if there is a bit else false
     */
    public boolean contains(int val) {
        if (!checkThreshold(val)) {
            return false;
        }
        return arrData[getIndex(val)].contains(val % elemNum);
    }

    /**
     * Remove bit at <code>val</code>
     * It decrease size if there is an empty block and makes defragmentation
     *
     * @param val number of bit to remove
     */
    public void remove(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        arrData[getIndex(val)].remove(val % elemNum);
        IntSet tmp = checkOnDefrag(this);
        this.arrData = tmp.arrData;
    }

    /**
     * Substract two {@link IntSet} objects using {@link Int64#minus(Int64)} method
     *
     * @param other Another {@link IntSet} object
     * @return new {@link IntSet} object
     */
    public IntSet minus(IntSet other) {
        int l = min(this.arrData.length, other.arrData.length);
        IntSet res = new IntSet(max(this.arrData.length, other.arrData.length));
        if (this.arrData.length > other.arrData.length) {
            System.arraycopy(this.arrData, 0, res.arrData, 0, res.arrData.length);
        } else {
            System.arraycopy(other.arrData, 0, res.arrData, 0, res.arrData.length);
        }
        for (int i = 0; i < l; i++) {
            res.arrData[i] = this.arrData[i].minus(other.arrData[i]);
        }
        return res;
    }

    /**
     * Union two {@link IntSet} objects using {@link Int64#union(Int64)}} method
     *
     * @param other Another {@link IntSet} object
     * @return new {@link IntSet} object
     */
    public IntSet union(IntSet other) {
        int l = min(this.arrData.length, other.arrData.length);
        IntSet res = new IntSet(max(this.arrData.length, other.arrData.length));
        if (this.arrData.length > other.arrData.length) {
            System.arraycopy(this.arrData, 0, res.arrData, 0, res.arrData.length);
        } else {
            System.arraycopy(other.arrData, 0, res.arrData, 0, res.arrData.length);
        }
        for (int i = 0; i < l; i++) {
            res.arrData[i] = this.arrData[i].union(other.arrData[i]);
        }
        return res;
    }

    /**
     * Intersect two {@link IntSet} objects using {@link Int64#intersection(Int64)}} method
     *
     * @param other Another {@link IntSet} object
     * @return new {@link IntSet} object
     */
    public IntSet intersection(IntSet other) {
        int l = min(this.arrData.length, other.arrData.length);
        IntSet res = new IntSet(min(this.arrData.length, other.arrData.length));
        if (this.arrData.length < other.arrData.length) {
            System.arraycopy(this.arrData, 0, res.arrData, 0, res.arrData.length);
        } else {
            System.arraycopy(other.arrData, 0, res.arrData, 0, res.arrData.length);
        }
        for (int i = 0; i < l; i++) {
            res.arrData[i] = this.arrData[i].intersection(other.arrData[i]);
        }

        res = checkOnDefrag(res);
        return res;
    }

    /**
     * Check on substring for called {@link IntSet} object using {@link Int64#isSubsetOf(Int64)} method
     *
     * @param other Another {@link IntSet} object
     * @return false if there is not a substring in other else true
     */
    public boolean isSubsetetOf(IntSet other) {
        int l = min(this.arrData.length, other.arrData.length);
        IntSet res = new IntSet(max(this.arrData.length, other.arrData.length));
        if (this.arrData.length > other.arrData.length) {
            System.arraycopy(this.arrData, 0, res.arrData, 0, res.arrData.length);
        } else {
            System.arraycopy(other.arrData, 0, res.arrData, 0, res.arrData.length);
        }
        boolean flag = true;
        for (int i = 0; i < l; i++) {
            flag &= this.arrData[i].isSubsetOf(other.arrData[i]);
        }
        return flag;
    }


    /**
     * Check if value in boundaries <code>0</code> and {@link #curretSize}
     *
     * @param val value to check
     * @return true if in boundaries else false
     */
    private boolean checkThreshold(int val) {
        return !(val < 0 || val > curretSize);
    }

    /**
     * Print all {@link Int64#data} values
     */
    public void printIntSet() {
        for (int i = 0; i < arrData.length; i++) {
            System.out.printf("ID:%d [%d] -> ", this.ID, i);
            this.arrData[i].int64Print();
        }
        System.out.println();
    }

    /**
     * Get max value
     *
     * @param a input value
     * @param b input value
     * @return max of <code>a</code> and <code>b</code> values
     */
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    /**
     * Get min value
     *
     * @param a input value
     * @param b input value
     * @return min of <code>a</code> and <code>b</code> values
     */
    private int min(int a, int b) {
        return (a > b) ? b : a;
    }

    /**
     * Get index of block where to input <code>val</code> in {@link IntSet}
     *
     * @param val Value to input
     * @return number of index where to input <code>val</code>
     */
    private int getIndex(int val) {
        int index = (int) Math.ceil(val / elemNum);
        if (index >= size)
            return size - 1;
        return index;
    }

    /**
     * Check if there are empty blocks to remove them
     *
     * @param res Input {@link IntSet} object
     * @return new defragged {@link IntSet} object
     */
    private IntSet checkOnDefrag(IntSet res) {
        int emptyNum = 0;
        Int64 zero = new Int64();
        for (int i = 0; i < res.arrData.length; i++) {
            if ((res.arrData[i].getVal() ^ zero.getVal()) == 0) {
                emptyNum++;
            }
        }
        if (emptyNum > 0) {
            int inc = 0;
            globalID--;
            IntSet newSet = new IntSet(size - emptyNum + 1);

            for (int i = 0; i < res.arrData.length; i++) {
                if ((res.arrData[i].getVal() ^ zero.getVal()) != 0) {
                    newSet.arrData[i - inc] = res.arrData[i];
                } else {
                    inc++;
                    res.arrData[i] = null;
                }
            }

            return newSet;
        } else
            return res;
    }
}
