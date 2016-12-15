package org.cacheframework.cache;


import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 抽象缓存缓存工厂名工厂
 * @Author: zhangyan
 * @Date:2016/12/12.15:43
 * @Version：V1.0
 */
public abstract class AbstractNameCachingCacheFactory<A extends Annotation, T extends ICache> extends
        AbstractApplicationContextAwareCacheFactory<A, T> {

    private final ConcurrentMap<A, String> nameCacheMap = new ConcurrentHashMap<>();

    public AbstractNameCachingCacheFactory(Class<T> cacheType){
        super(cacheType);
    }

    /**
     * 从 application context中获取符合条件的的缓存容器的名字
     *
     * @param annotation 注解信息
     * @return 缓存容器的名字
     */
    @Override
    protected String getMatchCacheNameFromApplicationContext(A annotation) {
        String cacheName = this.nameCacheMap.get(annotation);
        if (null != cacheName) {
            return cacheName;
        }

        cacheName = super.getMatchCacheNameFromApplicationContext(annotation);

        this.nameCacheMap.put(annotation, cacheName);

        return cacheName;
    }
}
