package com.xd.batch.cache;

import com.xd.batch.bean.AccessType;
import com.xd.batch.bean.AccessWrapper;

public interface ICacheLocal extends ICacheDefinition{

     <T> boolean putAccess(Class clazz, AccessWrapper<T> t);

    Object getAccess(Class clazz, String element, AccessType accessType);

    boolean containsClazz(Class clazz,AccessType accessType);

    void putSimple(String key,Object value);

    void getAndPutSimple(String key,Object value);

    Object getSimple(String key);

    void removeSimple(String... keys);

    boolean containsKeySimple(String key);
}
