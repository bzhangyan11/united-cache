package org.cacheframework.context;


import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Dscription： 缓存key解析器 组合器
 * @Author: zhangyan
 * @Date:2016/12/13.10:16
 * @Version：V1.0
 */
public class CacheKeyResolverComposite implements ICacheKeyResolver {

    private final List<ICacheKeyResolver> cacheKeyResolvers;

    public CacheKeyResolverComposite(List<ICacheKeyResolver> cacheKeyResolvers) {
        List<ICacheKeyResolver> enableCacheKeyResolvers = new ArrayList<>();

        for (ICacheKeyResolver cacheKeyResolver : cacheKeyResolvers) {
            if(cacheKeyResolver.enable()){
                enableCacheKeyResolvers.add(cacheKeyResolver);
            }
        }

        Collections.sort(enableCacheKeyResolvers, AnnotationAwareOrderComparator.INSTANCE);

        this.cacheKeyResolvers = enableCacheKeyResolvers;
    }

    /**
     * 是否启用该缓存处理器
     *
     * @return 是否启用
     */
    @Override
    public boolean enable() {
        return true;
    }

    /**
     * 此缓存key处理器是否支持此方法
     *
     * @param method 方法
     * @return 是否支持
     */
    @Override
    public boolean support(Method method) {
        if (cacheKeyResolvers.isEmpty()) {
            return false;
        }

        return null != this.getSupportedCacheResolver(method);
    }

    /**
     * 获取支持的缓存处理器
     *
     * @param method 方法
     * @return 缓存处理器
     */
    protected ICacheKeyResolver getSupportedCacheResolver(Method method) {
        for (ICacheKeyResolver cacheKeyResolver : cacheKeyResolvers) {
            if (cacheKeyResolver.support(method)) {
                return cacheKeyResolver;
            }
        }

        return null;
    }

    /**
     * 得出缓存 key
     *
     * @param method 方法
     * @param args   参数
     * @return 缓存key
     */
    @Override
    public Object resolve(Method method, Object[] args) {
        ICacheKeyResolver cacheKeyResolver = this.getSupportedCacheResolver(method);
        if (null == cacheKeyResolver) {
            throw new IllegalArgumentException("can't find matched cacheKeyResolver");
        }

        return cacheKeyResolver.resolve(method, args);
    }
}
