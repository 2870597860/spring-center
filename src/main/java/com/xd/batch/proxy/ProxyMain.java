package com.xd.batch.proxy;

import com.xd.batch.proxy.jdk.ConnectionProxy;
import com.xd.batch.proxy.jdk.IConnect;
import com.xd.batch.proxy.jdk.IConnectImpl;
import org.springframework.cglib.proxy.Enhancer;

public class ProxyMain {
    public static void main(String[] args) {
        jdk();
        cglib();
    }

    public static void jdk() {
        IConnectImpl connect = new IConnectImpl();
        ConnectionProxy proxy = new ConnectionProxy();
        IConnect instance = proxy.getInstance(connect);
        instance.connect(new Object());
    }

    public static void cglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(IConnectImpl.class);
        enhancer.setCallback(new com.xd.batch.proxy.cglib.ConnectionProxy());

        IConnectImpl o = (IConnectImpl) enhancer.create();
        o.connect(new Object());
    }
}
