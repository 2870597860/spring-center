package com.xd.batch;

import org.openjdk.jol.info.ClassLayout;

public class MainTest {
    public static void main(String[] args) {
        Object[] objects=new Object[1];
        Object object = new Object();
        System.out.println(ClassLayout.parseClass(object.getClass()).toPrintable());

    }
}
