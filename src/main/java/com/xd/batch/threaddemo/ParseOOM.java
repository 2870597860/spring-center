package com.xd.batch.threaddemo;

public class ParseOOM {
    public static void main(String[] args) {
        long num = 1000000L;
        for (long i = 0; i < num; i++) {
            Object obj = new Object();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
