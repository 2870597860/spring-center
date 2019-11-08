package com.xd.batch.main;

import com.xd.batch.function.DemoFunction;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        //m.testFunc();
        DemoFunction demoFunction = new DemoFunction();
        demoFunction.send("xiaodai",k -> k+"sasas");


    }

    public void testGet(){
        Person person = new Person();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            person.getAddress();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
    public void testJdkReflect() throws Exception {
        Person person = new Person();
        long start = System.currentTimeMillis();
        Method method = person.getClass().getMethod("getAddress");
        for (int i = 0; i < 100000000; i++) {
            method.invoke(person);
        }
        long end = System.currentTimeMillis();
        System.out.println("timeout=" + (end - start));
    }

    public String testFunc(){
       HashMap<String,String> map =new HashMap<>();
        String s = map.computeIfAbsent("1", key -> "xiaodai");
        System.out.println(s);
        String sw = map.computeIfAbsent("1", key -> "xiaodai2222");
        System.out.println(sw);
        return "";
    }
}
