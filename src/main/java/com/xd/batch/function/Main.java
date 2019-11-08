package com.xd.batch.function;

public class Main {
    public static void main(String[] args) {
        BizService bizService = new BizService();
        bizService.process("sasasa",(message -> System.out.println(message)));

        byte cd = 1;
        System.out.println(String.valueOf(cd));

        System.out.println((2 & 1));
    }
}
