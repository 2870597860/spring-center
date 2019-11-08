package com.xd.batch.bean;

public enum Operator {
    Add(0),
    Update(1),
    Delete(2);

    private int index;

    Operator(int i) {
    }

    public int getIndex() {
        return index;
    }
}
