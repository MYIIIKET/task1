package com.mylllket_inc.app;

/**
 * Class for storing <code>int</code> values in <code>long</code> by setting bits
 */

public class Int64 {
    /**
     * Field containing <code>int</code> value
     */
    private long data = 0;
    /**
     * Low bound
     */
    private final static byte lowThresh = 0;
    /**
     * High bound
     */
    private final static byte highThresh = 63;

    /**
     * Default constructor
     */
    public Int64() {
        data = 0;
    }


    /**
     * Initialization constructor
     *
     * @param data - initialize parameter
     */
    private Int64(long data) {
        this.data = data;
    }


    /**
     * Method to add <code>int</code> value to {@link #data}
     *
     * @param val
     */
    public void add(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        data |= 1L << val;
    }

    /**
     * Check on containing <code>int</code>value in {@link #data}
     *
     * @param val
     * @return true if there is a value else false
     */
    public boolean contains(int val) {
        if (!checkThreshold(val))
            return false;
        final long res = data & (1L << val);
        return res != 0;
    }


    /**
     * Remove <code>int</code> value from {@link #data}
     *
     * @param val
     */
    public void remove(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        data &= ~(1L << val);
    }

    /**
     * Unite two <code>long</code> values
     *
     * @param other Another {@link #Int64()} object
     * @return Union of two {@link #data} values as new {@link #Int64()} object
     */
    public Int64 union(Int64 other) {
        return new Int64(this.data | other.data);
    }


    /**
     * Intersect two <code>long</code> values
     *
     * @param other Another {@link #Int64()} object
     * @return Intersect of two {@link #data} values as new {@link #Int64()} object
     */
    public Int64 intersection(Int64 other) {
        return new Int64(this.data & other.data);
    }

    /**
     * Substract two {@link #data} values of two {@link #Int64()} objects
     *
     * @param other Another {@link #Int64()} object
     * @return Substract of two {@link #data} values as new {@link #Int64()} object
     */
    public Int64 minus(Int64 other) {
        return new Int64(this.data - other.data);
    }

    /**
     * Check on substring for called {@link #Int64()} object
     *
     * @param other Another {@link #Int64()} object
     * @return false if there is not a substring in other else true
     */
    public boolean isSubsetOf(Int64 other) {
        if (this.bitNum() > other.bitNum()) {
            return false;
        }
        Int64 tmp = new Int64();
        for (int i = 0; i < other.bitNum() + 1; i++) {
            for (int j = 0; j < this.bitNum() + (i + 1); j++) {
                tmp.add(j);
            }
            tmp = tmp.intersection(other);
            tmp.data >>= i;
            if ((tmp.data ^ this.data) == 0) {
                return true;
            }
        }
        return false;
    }

    //auxiliary methods---------------------------------------------------------------------------------------------

    /**
     * Method returning {@link #data} value for called object
     *
     * @return {@link #data} value
     */
    public long getVal() {
        return this.data;
    }

    //int64Print() - print containing of data

    /**
     * Print containing of {@link #data}
     */
    public void int64Print() {
        System.out.println(this.data + " -> " + Long.toBinaryString(this.data));
    }

    /**
     * Check if value in boundaries {@link #highThresh} and {@link #lowThresh}
     *
     * @param val value to check
     * @return true if in boundaries else false
     */
    private boolean checkThreshold(int val) {
        return !(val < lowThresh || val > highThresh);
    }

    /**
     * Method to get number of bits
     *
     * @return number of high bit in {@link #data} value for called {@link #Int64()} object
     */
    private int bitNum() {
        return (int) (Math.ceil(Math.log(this.data) / Math.log(2)));
    }

}



