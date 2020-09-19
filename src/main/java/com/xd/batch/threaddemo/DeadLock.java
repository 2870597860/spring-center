package com.xd.batch.threaddemo;

public class DeadLock implements Runnable{

    int field_1;
    private Object lock_1 = new int[1];
    int field_2;
    private Object lock_2 = new int[1];

    public void method1(int value){
        synchronized (lock_1){
            System.out.println(Thread.currentThread().getName()+"lock1");
            synchronized (lock_2){
                field_1 =0;
                field_2 =0;
            }
        }
    }

    public void method2(int value){
        synchronized (lock_2){
            System.out.println(Thread.currentThread().getName()+"lock1");
            synchronized (lock_1){
                field_1 =0;
                field_2 =0;
            }
        }
    }

    @Override
    public void run() {
        method1(2);
        method2(1);
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        Thread thread = new Thread(deadLock,"Thread monkey");
        Thread thread2 = new Thread(deadLock,"THread BB");
        thread.start();
        thread2.start();
    }
}
