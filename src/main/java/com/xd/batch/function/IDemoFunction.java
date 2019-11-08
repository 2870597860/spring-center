package com.xd.batch.function;

import java.util.function.Function;

public interface IDemoFunction {

   <K,V> void send(K msg, Function<? super K, ? extends V> mappingFunction);
}
