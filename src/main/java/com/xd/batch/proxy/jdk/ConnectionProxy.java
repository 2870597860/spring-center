package com.xd.batch.proxy.jdk;

import com.xd.batch.proxy.jdk.IConnect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConnectionProxy implements InvocationHandler {

    private IConnect iConnect;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(iConnect, args);
        System.out.println("after");
        return result;
    }

    public IConnect getInstance(IConnect iConnect){
        this.iConnect = iConnect;
        return (IConnect) Proxy.newProxyInstance(iConnect.getClass().getClassLoader(),iConnect.getClass().getInterfaces(),this);
    }

}
