package com.xd.batch.cache;

import com.xd.batch.util.ClazzLoaderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleListableCacheFactory implements ICacheRegistry{


    private final Map<String, ICacheDefinition> cacheDefinitionMap = new ConcurrentHashMap<>(256);

    @PostConstruct
    public void createCache(){
        ArrayList<Class> allClassOfICacheDefinition = ClazzLoaderUtil.getAllClassByInterface(ICacheDefinition.class);
        allClassOfICacheDefinition.forEach(item -> {
            try {
                if(item.isInterface()){
                    return;
                }
                registerCacheDefinition(item.getSimpleName(), (ICacheDefinition) item.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void registerCacheDefinition(String cacheName, ICacheDefinition iCacheDefinition) {
        cacheDefinitionMap.put(cacheName,iCacheDefinition);
    }

    @Override
    public void removeBeanDefinition(String cacheName) {
        cacheDefinitionMap.remove(cacheName);
    }

    @Override
    public ICacheDefinition getCacheDefinition(String cacheName) {
        return cacheDefinitionMap.get(cacheName);
    }

}
