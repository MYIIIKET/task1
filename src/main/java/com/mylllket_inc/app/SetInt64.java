package com.mylllket_inc.app;

import java.util.Set;


public class SetInt64 {
    private long data = 0;  //field containing int values
    private final short lowThresh = 0;      //low bound
    private final short highThresh = 63;    //high bound

    //default constructor
    public SetInt64() {
        data = 0;
    }

    //initialization constructor
    //@param data - initialize parameter
    private SetInt64(long data) {
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

    //union(SetInt64) - method to unite two long values
    //@param other - SetInt64 type value to unite with
    //@return new SetInt64 value
    public SetInt64 union(SetInt64 other) {
        return new SetInt64(this.data | other.data);
    }

    //intersection(SetInt64) - method to unite two long values
    //@param other - SetInt64 type value to intersect with
    //@return new SetInt64 value
    public SetInt64 intersection(SetInt64 other) {
        return new SetInt64(this.data & other.data);
    }

    //difference(SetInt64) - compare two SetInt64 values
    //@param other - SetInt64 type value to compare with
    //@return false if different
    //@return true if equal
    public boolean difference(SetInt64 other) {
        return (this.data ^ other.data) == 0;
    }

    //isSubsetOf(SetInt64) - check on substring for called SetInt64 value
    //@param other - SetInt64 type value to find substring
    //@return true if there is a substring in other
    //@return false if there is not a substring in other
    public boolean isSubsetOf(SetInt64 other) {
        if (this.bitNum() >= other.bitNum()) {
            return false;
        }
        SetInt64 tmp = new SetInt64();
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
    //@return number of bits for called SetInt64 object
    private int bitNum() {
        return (int) (Math.ceil(Math.log(this.data) / Math.log(2)));
    }

}



