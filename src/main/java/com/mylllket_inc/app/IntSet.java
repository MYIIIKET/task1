package com.mylllket_inc.app;


public class IntSet {
    private long data = 0;  //field containing int values
    private final short lowThresh = 0;      //low bound
    private final short highThresh = 63;    //high bound

    //default constructor
    public IntSet() {
        data = 0;
    }

    //initialization constructor
    //@param data - initialize parameter
    private IntSet(long data) {
        this.data = data;
    }

    //add(int) - add int value to @data field
    //@param val - int value to add into long
    public void add(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        data |= 1L << val;
    }

    //contain(int) - check on containing int value in @data field
    //@param val - check if bit at val is set in data
    //@return false if there is no a value
    //@return true if there is a value
    public boolean contains(int val) {
        if (!checkThreshold(val))
            return false;
        final long res = data & (1L << val);
        return res != 0;
    }

    //remove(int) - remove int value from data field
    //@param val - bit number to remove
    public void remove(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        data &= ~(1L << val);
    }

    //union(IntSet) - method to unite two long values
    //@param other - IntSet type value to unite with
    //@return new IntSet value
    public IntSet union(IntSet other) {
        return new IntSet(this.data | other.data);
    }

    //intersection(IntSet) - method to unite two long values
    //@param other - IntSet type value to intersect with
    //@return new IntSet value
    public IntSet intersection(IntSet other) {
        return new IntSet(this.data & other.data);
    }

    //difference(IntSet) - compare two IntSet values
    //@param other - IntSet type value to compare with
    //@return false if different
    //@return true if equal
    public boolean difference(IntSet other) {
        return (this.data ^ other.data) == 0;
    }

    //isSubsetOf(IntSet) - check on substring for called IntSet value
    //@param other - IntSet type value to find substring
    //@return true if there is a substring in other
    //@return false if there is not a substring in other
    public boolean isSubsetOf(IntSet other) {
        if (this.bitNum() >= other.bitNum()) {
            return false;
        }
        IntSet tmp = new IntSet();
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

    //getVal()
    //@return value of data
    public long getVal() {
        return this.data;
    }

    //int64Print() - print containing of data
    public void int64Print() {
        System.out.println(this.data + " -> " + Long.toBinaryString(this.data));
    }

    //checkThreshold(int val) - check if value in boundaries [0; 62]
    //@param val - value to check
    //@return true if in boundaries
    //@return false if out of bound
    private boolean checkThreshold(int val) {
        return !(val < lowThresh || val > highThresh);
    }

    //bitNum()
    //@return number of bits for called IntSet object
    private int bitNum() {
        return (int) (Math.ceil(Math.log(this.data) / Math.log(2)));
    }

}



