package org.cacheframework.context;


import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 缓存键处理器缓存组合器
 * @Author: zhangyan
 * @Date:2016/12/13.10:51
 * @Version：V1.0
 */
public class CacheKeyResolverCachingComposite extends CacheKeyResolverComposite {

    private final ConcurrentMap<Method, ICacheKeyResolver> methodToCacheKeyResolverMap = new
            ConcurrentHashMap<>();

    private final ConcurrentMap<Method, Boolean> methodToIsSupportedMap = new
            ConcurrentHashMap<>();

    public CacheKeyResolverCachingComposite(List<ICacheKeyResolver> cacheKeyResolvers) {
        super(cacheKeyResolvers);
    }

    /**
     * 此缓存key处理器是否支持此方法
     *
     * @param method 方法
     * @return 是否支持
     */
    @Override
    public boolean support(Method method) {
        Boolean isSupported = this.methodToIsSupportedMap.get(method);
        if(null != isSupported){
            return isSupported;
        }

        isSupported = super.support(method);

        this.methodToIsSupportedMap.putIfAbsent(method,isSupported);

        return isSupported;
    }

    /**
     * 获取支持的缓存处理器
     *
     * @param method 方法
     * @return 缓存处理器
     */
    @Override
    protected ICacheKeyResolver getSupportedCacheResolver(Method method) {
        ICacheKeyResolver cacheKeyResolver = this.methodToCacheKeyResolverMap.get(method);
        if (null != cacheKeyResolver) {
            return cacheKeyResolver;
        }

        cacheKeyResolver = super.getSupportedCacheResolver(method);
        if(null == cacheKeyResolver){
            throw new IllegalArgumentException("can't find a suitable cache key resolver");
        }

        this.methodToCacheKeyResolverMap.putIfAbsent(method, cacheKeyResolver);

        return cacheKeyResolver;
    }
}
