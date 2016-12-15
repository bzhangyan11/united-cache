package org.cacheframework.cache;


import org.cacheframework.annotation.SoftCache;

/**
 * @Dscription： 全局软引用缓存工厂
 * @Author: zhangyan
 * @Date:2016/12/14.15:27
 * @Version：V1.0
 */
public class GlobalSoftCacheFactory extends AbstractNameCachingCacheFactory<SoftCache,
        GlobalSoftCache> implements ISoftCacheFactory{

    public GlobalSoftCacheFactory() {
        super(GlobalSoftCache.class);
    }

    /**
     * 初始化缓存
     *
     * @param cache 缓存注解信息
     */
    @Override
    protected GlobalSoftCache initCache(GlobalSoftCache cache, SoftCache softCache) {
        if(softCache.enableExpiry()){
            throw new IllegalArgumentException("软引用过期配置暂不支持");
        }

        return cache;
    }

}
