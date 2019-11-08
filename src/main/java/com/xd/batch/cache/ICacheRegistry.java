package com.xd.batch.cache;

public interface ICacheRegistry {
    void registerCacheDefinition(String cacheName, ICacheDefinition iCacheDefinition);

    void removeBeanDefinition(String cacheName);

    ICacheDefinition getCacheDefinition(String cacheName);


}
