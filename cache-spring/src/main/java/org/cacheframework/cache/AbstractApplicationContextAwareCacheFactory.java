package org.cacheframework.cache;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;

/**
 * @Dscription： 抽象容器感知factory
 * @Author: zhangyan
 * @Date:2016/12/12.14:58
 * @Version：V1.0
 */
public abstract class AbstractApplicationContextAwareCacheFactory<A extends Annotation, T extends
        ICache> implements ICacheFactory<A>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final Class<T> cacheType;

    public AbstractApplicationContextAwareCacheFactory(Class<T> cacheType) {
        if (null == cacheType) {
            throw new IllegalArgumentException("cache type can't be null");
        }
        this.cacheType = cacheType;
    }

    @Override
    public T getCache(A annotation) {

        String cacheName = this.getMatchCacheNameFromApplicationContext(annotation);

        T cache = this.getCacheFromApplicationContext(cacheName);

        return this.initCache(cache, annotation);
    }

    /**
     * 初始化缓存
     *
     * @param cache 缓存注解信息
     */
    protected abstract T initCache(T cache, A annotation);

    @SuppressWarnings("unchecked")
    private T getCacheFromApplicationContext(String cacheName) {
        return (T) this.applicationContext.getBean(cacheName);
    }

    /**
     * 从 application context中获取符合条件的的缓存容器的名字
     *
     * @param annotation 注解信息
     * @return 缓存容器的名字
     */
    protected String getMatchCacheNameFromApplicationContext(A annotation) {

        if (!StringUtils.isEmpty(this.getCacheName(annotation)) && this
                .isApplicationContextContainCacheByQualifierName(this.getCacheName(annotation),
                        this.getCacheType())) {
            return this.getCacheName(annotation);
        }

        Class<T> cacheType = this.getCacheType();
        if (null == cacheType) {
            throw new IllegalArgumentException("cacheType can't be null");
        }

        String[] candidateNames = this.applicationContext.getBeanNamesForType(cacheType);
        if (ArrayUtils.isEmpty(candidateNames)) {
            throw new IllegalArgumentException("can't find cache from application context for " +
                    "cache factory :" + this.getClass().getName());
        }

        return this.chooseBestCandidate(candidateNames, annotation);
    }

    /**
     * 选择最佳匹配的候选缓存名称
     *
     * @param candidateNames 缓存名称
     * @return 最佳匹配候选缓存名称
     */
    protected String chooseBestCandidate(String[] candidateNames, Annotation annotation) {
        if (1 < candidateNames.length) {
            throw new IllegalArgumentException("duplication cache type in application context for" +
                    " cache factory :" + this.getClass().getName());
        }

        return candidateNames[0];
    }

    /**
     * 判断 application context中是否存在指定的缓存容器
     *
     * @param name      缓存名字
     * @param cacheType 缓存类型
     * @return 是否存在
     */
    private boolean isApplicationContextContainCacheByQualifierName(String name, Class<? extends
            ICache> cacheType) {
        return null != this.applicationContext.getBean(name, cacheType == null ? ICache.class :
                cacheType);
    }

    private Class<T> getCacheType() {
        return this.cacheType;
    }

    /**
     * 指定缓存名称，如果有多个缓存可以不指定
     *
     * @param annotation 注解信息
     * @return 缓存名
     */
    protected String getCacheName(A annotation) {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
