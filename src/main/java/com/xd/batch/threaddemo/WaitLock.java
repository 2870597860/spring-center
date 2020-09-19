package com.xd.batch.threaddemo;

public class WaitLock implements Runnable{

    private Object lock_1 = new int[1];

    public void method1(int value){
        synchronized (lock_1){
            System.out.println(Thread.currentThread().getName()+"lock1");
            while(true){

            }
        }
    }

    public void method2(int value){
        synchronized (lock_1){
            System.out.println(Thread.currentThread().getName()+"lock1");
            while(true){

            }
        }
    }


    @Override
    public void run() {
        method1(1);
        method1(2);
    }

    public static void main(String[] args) {
        WaitLock waitLock = new WaitLock();
        Thread thread = new Thread(waitLock,"Thread ss");
        thread.start();
    }
}
