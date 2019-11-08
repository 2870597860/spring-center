package com.xd.batch.function;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

public class DemoFunction implements IDemoFunction{

    @Override
    public <K, V> void send(K msg, Function<? super K, ? extends V> mappingFunction) {
        if(msg instanceof String && StringUtils.isNotBlank((String)msg)){
            V apply = mappingFunction.apply(msg);
            System.out.println(apply.toString());
        }
    }
}
