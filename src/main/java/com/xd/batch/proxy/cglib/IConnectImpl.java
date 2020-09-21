package com.xd.batch.proxy.cglib;

public class IConnectImpl implements IConnect {
    @Override
    public boolean connect(Object obj) {
        System.out.println("connect sucess");
        return true;
    }
}
