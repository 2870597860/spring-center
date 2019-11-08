package com.xd.batch.cache;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.xd.batch.RedisConstants;
import com.xd.batch.bean.AccessType;
import com.xd.batch.bean.AccessWrapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hutool.core.util.ObjectUtil.isNotNull;

public class LocalCache implements ICacheLocal{

    // cache reflectMethodClass
    private final Map<String, List<AccessWrapper>> reflectCacheMethodMap = new ConcurrentHashMap<>(RedisConstants.ReflectMapInitSize);
    // cache reflectFieldClass
    private final Map<String, List<AccessWrapper>> reflectCacheFieldMap = new ConcurrentHashMap<>(RedisConstants.ReflectMapInitSize);
    // cache common
    private final Map<String,Object> map = new ConcurrentHashMap<>();

    /**
     * @param clazz 反射类
     * @param t 封装的反射类的信息
     * @param <T> FieldAccess/MethodAccess
     * @return
     */
    @Override
    public <T> boolean putAccess(Class clazz, AccessWrapper<T> t) {
        try {
            if (t.getAccess() instanceof FieldAccess) {
                List<AccessWrapper> accessWrappers = reflectCacheFieldMap.computeIfAbsent(clazz.getName(), key -> new ArrayList<>());
                accessWrappers.add(t);
            }else if(t.getAccess() instanceof MethodAccess){
                List<AccessWrapper> accessWrappers = reflectCacheMethodMap.computeIfAbsent(clazz.getName(), key -> new ArrayList<>());
                accessWrappers.add(t);
            }
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    /**
     *
     * @param clazz 缓存的clazz
     * @param name class的属性名或者方法名
     * @param accessType 类型（名/方法名）
     * @return
     */
    @Override
    public Object getAccess(Class clazz, String name, AccessType accessType) {
        List<AccessWrapper> accessWrappers = null;
        if(accessType == AccessType.Field){
            accessWrappers = reflectCacheMethodMap.get(clazz.getName());
        }else if(accessType == AccessType.Method){
            accessWrappers = reflectCacheMethodMap.get(clazz.getName());
        }
        for (AccessWrapper accessWrapper : accessWrappers) {
            if(accessWrapper.getPropertyName().equals(name)){
                return accessWrapper;
            }
        }
        return null;
    }

    @Override
    public boolean containsClazz(Class clazz,AccessType accessType){
        if(accessType == AccessType.Field){
            return reflectCacheFieldMap.containsKey(clazz);
        }else if(accessType == AccessType.Method){
            return reflectCacheMethodMap.containsKey(clazz);
        }
        return false;
    }

    /**
     * 普通的缓存
     * @param key
     * @param value
     */
    @Override
    public void putSimple(String key, Object value){
        map.put(key,value);
    }

    @Override
    public void getAndPutSimple(String key, Object value) {
        synchronized (this){
            Object o = map.get(key);
            if(o instanceof List){
                if(value instanceof Collection){
                    ((List)o).addAll((Collection)value);
                }else if(value instanceof Map){

                }else if(value instanceof Set){

                }
            }
        }
    }

    @Override
    public Object getSimple(String key){
       return map.get(key);
    }
    @Override
    public void removeSimple(String... keys){
        if(isNotNull(keys)){
            for (String key : keys) {
                map.remove(key);
            }
        }
    }
    @Override
    public boolean containsKeySimple(String key){
        return map.containsKey(key);
    }

}
