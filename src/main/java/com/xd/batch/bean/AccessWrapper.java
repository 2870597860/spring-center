package com.xd.batch.bean;

public class AccessWrapper<T> {
    private T access;
    private String propertyName;
    private Class fieldType;
    private int index;

    public AccessWrapper(T access, String propertyName, int index) {
        this.access = access;
        this.propertyName = propertyName;
        this.index = index;
    }

    public Class getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class fieldType) {
        this.fieldType = fieldType;
    }

    public T getAccess() {
        return access;
    }

    public void setAccess(T access) {
        this.access = access;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
