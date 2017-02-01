package com.mylllket_inc.app;


public class IntSet {
    private Int64[] arrData;
    private static int size = 0;
    private final static int baseSize = 63;
    private final static int elemNum = 64;
    private int curretSize = 0;

    public IntSet() {
        if (size == 0) {
            size++;
            arrData = new Int64[size];
            arrData[size - 1] = new Int64();
            curretSize=baseSize;
        }
    }

    public void add(int val) {
        if (val > curretSize) {
            curretSize += elemNum;
            size++;
            Int64[] newArr = new Int64[size];
            System.arraycopy(arrData, 0, newArr, 0, arrData.length);
            newArr[size - 1] = new Int64();
            arrData = newArr;
        }
        int index = getIndex(val);
        arrData[index].add(val % elemNum);
    }

    public void printIntSet() {
        for (int i = 0; i < arrData.length; i++) {
            System.out.printf("[%d] -> ", i);
            this.arrData[i].int64Print();
        }
    }

    private int getIndex(int val) {
        int index = (int) Math.ceil(val / elemNum);
        if (index >= size)
            return size - 1;
        return index;
    }
}
