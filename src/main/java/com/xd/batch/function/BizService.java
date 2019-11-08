package com.xd.batch.function;

import static java.util.Objects.nonNull;

public class BizService {
    void process(String data,DefinneFunction definneFunction){
        if(nonNull(data)){
            definneFunction.doProcess(data);
        }
    }
}
