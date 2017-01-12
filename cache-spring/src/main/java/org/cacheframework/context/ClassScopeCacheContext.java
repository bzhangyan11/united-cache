package org.cacheframework.context;


import org.cacheframework.annotation.Cache;
import org.cacheframework.cache.ICache;
import org.cacheframework.cache.ICacheFactory;
import org.cacheframework.utils.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Dscription： 类生命周期缓存运行环境上下文
 * @Author: zhangyan
 * @Date:2016/12/13.11:27
 * @Version：V1.0
 */
public class ClassScopeCacheContext implements IDispatchableCacheContext,
        ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final ConcurrentMap<Method, ICache> cacheMap = new ConcurrentHashMap<>();

    private final Set<ICacheEventListener> cacheContextListenerList = new
            CopyOnWriteArraySet<>();

    /**
     * 获取缓存
     *
     * @param method 方法
     * @return 缓存
     */
    @Override
    public ICache getCache(Method method, Class<?> clz) {
        if (null == method) {
            return null;
        }

        ICache cache = this.cacheMap.get(method);
        if (null != cache) {
            return cache;
        }

        synchronized (method) {
            cache = this.cacheMap.get(method);
            if (null != cache) {
                return cache;
            }

            Cache metaCache = AnnotationUtils.findCacheMetaAnnotation(method, clz);
            if (null == metaCache) {
                throw new IllegalArgumentException("can't find meta cache annotation");
            }

            Annotation cacheAnnotation = AnnotationUtils.findCacheAnnotation(method, clz);
            if (null == cacheAnnotation) {
                throw new IllegalArgumentException("can't find cache annotation");
            }

            cache = this.getCacheFactoryFromApplicationContext(metaCache).getCache(cacheAnnotation);
            if (null == cache) {
                throw new IllegalArgumentException("cache can't be null");
            }

            this.cacheMap.putIfAbsent(method, cache);

            if (cache instanceof ICacheEventListener) {
                this.registerCacheContextEventListener((ICacheEventListener) cache);
            }

            if (cache instanceof ICacheContextEventDispatcherAware) {
                ((ICacheContextEventDispatcherAware) cache).setCacheContextEventDispatcher(this);
            }
        }

        return cache;

    }

    /**
     * 从applicationContext 中获取缓存工厂
     *
     * @param metaCache 缓存元注解
     * @return 缓存工厂
     */
    @SuppressWarnings("unchecked")
    private ICacheFactory<Annotation> getCacheFactoryFromApplicationContext(Cache metaCache) {
        if (null == metaCache.cacheFactoryType() || StringUtils.isEmpty(metaCache.cacheFactoryName())) {
            throw new IllegalArgumentException("meta cache info error");
        }

        ICacheFactory<Annotation> cacheFactory = this.applicationContext.getBean(metaCache
                .cacheFactoryName(), metaCache.cacheFactoryType());
        if (null == cacheFactory) {
            throw new IllegalArgumentException("can't find corresponding cache factory of meta " +
                    "cache annotation:[" + metaCache + "] ");
        }

        return cacheFactory;
    }


    /**
     * 触发缓存环境事件
     */
    @Override
    public void fireCacheContextEvenet(ICacheContextEvent cacheEvent) {
        for (ICacheEventListener cacheContextListener : this.cacheContextListenerList) {
            cacheContextListener.onEvent(cacheEvent);
        }
    }

    @Override
    public void registerCacheContextEventListener(ICacheEventListener cacheContextListener) {
        this.cacheContextListenerList.add(cacheContextListener);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
