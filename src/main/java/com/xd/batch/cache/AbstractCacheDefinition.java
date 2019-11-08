package com.xd.batch.cache;

import javax.annotation.Resource;

import static java.util.Objects.isNull;


public abstract class AbstractCacheDefinition {
    @Resource(name = "simpleListableCacheFactory")
    private ICacheRegistry cacheRegistry;

    private ICacheLocal localCache;

    private RedisCache redisCache;

    public ICacheLocal getLocalCache(){
        if(isNull(localCache)){
            this.localCache = (ICacheLocal) cacheRegistry.getCacheDefinition(LocalCache.class.getSimpleName());
        }
        return this.localCache;
    }

    public RedisCache getRedisCache(){
        if(isNull(redisCache)){
            this.redisCache = (RedisCache) cacheRegistry.getCacheDefinition(RedisCache.class.getSimpleName());
        }
        return this.redisCache;
    }

}
