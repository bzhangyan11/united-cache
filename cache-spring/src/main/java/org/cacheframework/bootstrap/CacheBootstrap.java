package org.cacheframework.bootstrap;


import org.cacheframework.bootstrap.aop.*;
import org.cacheframework.cache.GlobalSoftCache;
import org.cacheframework.cache.GlobalSoftCacheFactory;
import org.cacheframework.cache.ISoftCacheFactory;
import org.cacheframework.context.*;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;

/**
 * @Dscription： spring缓存启动引导器
 * @Author: zhangyan
 * @Date:2016/11/30.11:31
 * @Version：V1.0
 */
@Configuration
public class CacheBootstrap {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PointcutAdvisor cacheAdvisor() {
        return this.getCachePointCutAdvisor();
    }

    /**
     * 获取缓存切面配置,覆盖可扩展
     *
     * @return 缓存切面配置
     * @see CacheAdvisor
     */
    protected PointcutAdvisor getCachePointCutAdvisor() {
        return new CacheAdvisor(this.matchingMethodStrategy(), this.invoker(), this
                .dispatchableCacheContext());
    }

    @Bean
    public IMatchingMethodStrategy matchingMethodStrategy() {
        return this.getMatchingMethodStrategy();
    }

    @Bean
    public IInvoker invoker() {
        return this.getInvoker();
    }

    /**
     * 获取切面逻辑调用器,覆盖可扩展
     *
     * @return 切面逻辑调用器
     * @see DefaultInvoker
     */
    private IInvoker getInvoker() {
        return new DefaultInvoker(this.cacheKeyResolver());
    }

    @Bean
    public ICacheKeyResolver cacheKeyResolver() {
        return getCacheKeyResolver();
    }

    /**
     * 获得缓存key值处理器，可以用组合器组合使用下面提供的多种key处理器
     *
     * @see InterfaceCacheKeyResolver
     * @see VoidCacheKeyResolver
     * @see JsonCacheKeyResolver 未实现
     * @see ToStringAnnotationCacheKeyResolver 未实现
     */
    public ICacheKeyResolver getCacheKeyResolver(){
        return new CacheKeyResolverCachingComposite(Arrays.asList(voidCacheKeyResolver(),
                interfaceCacheKeyResolver()));
    }

    @Bean
    public VoidCacheKeyResolver voidCacheKeyResolver(){
        return new VoidCacheKeyResolver();
    }

    @Bean
    public InterfaceCacheKeyResolver interfaceCacheKeyResolver(){
        return new InterfaceCacheKeyResolver();
    }


    /**
     * 获取切面匹配策略，可通过and语义和or语义进行组合模式式扩展
     *
     * @return 返回匹配策略
     * @see AndMatchingMethodStrategyComposite
     * @see OrMatchingMethodStrategyComposite
     */
    protected IMatchingMethodStrategy getMatchingMethodStrategy() {
        return new OrMatchingMethodStrategyComposite(Arrays.asList
                (cacheAnnotationMatchingMethodStrategy(),
                        flushCacheAnnotationMatchingMethodStrategy()));
    }

    @Bean
    public CacheAnnotationMatchingMethodStrategy cacheAnnotationMatchingMethodStrategy() {
        return new CacheAnnotationMatchingMethodStrategy();
    }

    @Bean
    public CacheContextEventAnnotationMatchingMethodStrategy flushCacheAnnotationMatchingMethodStrategy(){
        return new CacheContextEventAnnotationMatchingMethodStrategy();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IDispatchableCacheContext dispatchableCacheContext() {
        return this.getDispatchableCacheContext();
    }

    /**
     * 获取可分发事件缓存上下文,生命周期为一个类实例,可扩展
     *
     * @return 可分发事件缓存上下文
     * @see ClassScopeCacheContext
     */
    protected IDispatchableCacheContext getDispatchableCacheContext() {
        return new ClassScopeCacheContext();
    }

    @Bean(name = ISoftCacheFactory.SOFT_CACHE_FACTORY)
    public GlobalSoftCacheFactory globalSoftCacheFactory() {
        return new GlobalSoftCacheFactory();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GlobalSoftCache globalSoftCache() {
        return new GlobalSoftCache();
    }


}
