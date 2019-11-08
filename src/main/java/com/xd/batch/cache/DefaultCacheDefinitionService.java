package com.xd.batch.cache;

import com.xd.batch.bean.Operator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCacheDefinitionService extends AbstractCacheDefinition{


    public <T> void operatorSimpleAdd(String key,List<T> list){
        boolean isExist = getLocalCache().containsKeySimple(key);
        if(isExist){
            getLocalCache().getAndPutSimple(key,list);
        }
    }
    public <T> void operatorSimpleUpdate(String key,List<T> list){
        getLocalCache().putSimple(key,list);
    }
    public <T> void operatorSimpleDelete(String key){
        getLocalCache().removeSimple(key);
    }

    public <T> void operatorSimple(String key,List<T> list, Operator operator){
        if (operator == Operator.Add) {
            operatorSimpleAdd(key,list);
        }
        if(operator == Operator.Update){
            operatorSimpleUpdate(key,list);
        }
        if(operator == Operator.Delete){
            operatorSimpleDelete(key);
        }

    }
}
