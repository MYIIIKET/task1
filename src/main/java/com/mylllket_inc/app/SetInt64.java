package com.mylllket_inc.app;

import java.util.Set;

public class SetInt64 {
    private long data = 0;
    private final short lowThresh = 0;
    private final short highThresh = 62;

    public SetInt64() {
        data = 0;
    }

    private SetInt64(long data) {
        this.data = data;
    }

    public void add(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        data |= 1L << val;
    }

    public boolean contains(int val) {
        if (!checkThreshold(val))
            return false;
        final long res = data & (1L << val);
        return res != 0;
    }

    public void remove(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        data &= ~(1L << val);
    }

    public SetInt64 union(SetInt64 other) {
        return new SetInt64(this.data | other.data);
    }

    public SetInt64 intersection(SetInt64 other) {
        return new SetInt64(this.data & other.data);
    }

    public SetInt64 difference(SetInt64 other) {
        if (this.data > other.data)
            return new SetInt64(this.data - other.data);
        else
            return new SetInt64(other.data - this.data);
    }

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

    public long getVal() {
        return this.data;
    }

    public void int64Print() {
        System.out.println(this.data + " -> " + Long.toBinaryString(this.data));
    }

    private boolean checkThreshold(int val) {
        return (val < lowThresh || val > highThresh) ? false : true;
    }

    private int bitNum() {
        return (int) (Math.ceil(Math.log(this.data) / Math.log(2)));
    }

}



