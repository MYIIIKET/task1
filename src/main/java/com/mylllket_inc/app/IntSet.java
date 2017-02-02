package com.mylllket_inc.app;


public class IntSet {
    private Int64[] arrData;
    private int size = 0;

    private final static int baseSize = 63;
    private final static int elemNum = 64;
    private int curretSize = 0;

    private static byte globalID = 0;
    private int ID = 0;

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

    public void add(int val) {
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

    public boolean contains(int val) {
        if (!checkThreshold(val)) {
            return false;
        }
        return arrData[getIndex(val)].contains(val % elemNum);
    }

    public void remove(int val) {
        if (!checkThreshold(val)) {
            return;
        }
        arrData[getIndex(val)].remove(val % elemNum);
        IntSet tmp = checkOnDefrag(this);
        this.arrData = tmp.arrData;
    }

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


    private boolean checkThreshold(int val) {
        return !(val < 0 || val > curretSize);
    }

    public void printIntSet() {
        for (int i = 0; i < arrData.length; i++) {
            System.out.printf("ID:%d [%d] -> ", this.ID, i);
            this.arrData[i].int64Print();
        }
        System.out.println();
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int min(int a, int b) {
        return (a > b) ? b : a;
    }

    private int getIndex(int val) {
        int index = (int) Math.ceil(val / elemNum);
        if (index >= size)
            return size - 1;
        return index;
    }

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
