package com.xd.batch.proxy.jdk;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
public interface IConnect {
    boolean connect(Object obj);
}
