package com.xd.batch.bean;

public enum AccessType {
    
    Field(0),
    Method(1);

    private int index;

    AccessType(int i) {
    }

    public int getIndex() {
        return index;
    }
}
