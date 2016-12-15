package org.cacheframework.bootstrap.aop;


import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.annotation.CacheContextEvent;
import org.cacheframework.cache.ICache;
import org.cacheframework.context.DefaultCacheContextEvent;
import org.cacheframework.context.ICacheContextEvent;
import org.cacheframework.context.ICacheKeyResolver;
import org.cacheframework.context.IDispatchableCacheContext;
import org.cacheframework.utils.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 默认调用器
 * @Author: zhangyan
 * @Date:2016/12/13.11:26
 * @Version：V1.0
 */
public class DefaultInvoker implements IInvoker {

    private static final Object NULL_OBJECT = new Object() {
        @Override
        public String toString() {
            return super.toString() + "null cache object";
        }
    };

    private final ICacheKeyResolver cacheKeyResolver;

    private final ConcurrentMap<Method, MethodType> methodToInvokeTypeCache = new
            ConcurrentHashMap<>();

    public DefaultInvoker(ICacheKeyResolver cacheKeyResolver) {
        this.cacheKeyResolver = cacheKeyResolver;
    }

    /**
     * 调用方法逻辑
     *
     * @param invocation   方法调用
     * @param cacheContext 缓存环境
     * @return 返回值
     */
    @Override
    public Object invoke(MethodInvocation invocation, IDispatchableCacheContext cacheContext) {
        MethodType methodType = this.getMethodTypeFromMethod(invocation.getMethod());

        return methodType.invokeStrategy.invoke(invocation, cacheContext, cacheKeyResolver);
    }

    /**
     * 获得方法调用类型
     *
     * @param method 方法
     * @return 方法类型
     */
    private MethodType getMethodTypeFromMethod(Method method) {
        MethodType methodType = methodToInvokeTypeCache.get(method);
        if (null != methodType) {
            return methodType;
        }

        if (null != AnnotationUtils.findCacheMetaAnnotation(method)) {
            methodToInvokeTypeCache.putIfAbsent(method, MethodType.CACHE);
            return MethodType.CACHE;
        }
        else if (null != AnnotationUtils.findMetaAnnotaion(method, CacheContextEvent.class)) {
            methodToInvokeTypeCache.putIfAbsent(method, MethodType.CACHE_CONTEXT_EVENT);
            return MethodType.CACHE_CONTEXT_EVENT;
        }
        else {
            methodToInvokeTypeCache.putIfAbsent(method, MethodType.NORMAL);
            return MethodType.NORMAL;
        }
    }

    /**
     * 执行方法策略
     */
    public interface InvokeStrategy {
        Object invoke(MethodInvocation invocation, IDispatchableCacheContext cacheContext, ICacheKeyResolver
                cacheKeyResolver);
    }

    private enum MethodType {
        /**
         * 缓存操作
         */
        CACHE(new InvokeStrategy() {

            @Override
            public Object invoke(MethodInvocation invocation, IDispatchableCacheContext cacheContext,
                                 ICacheKeyResolver cacheKeyResolver) {
                ICache cache = cacheContext.getCache(invocation.getMethod());

                Object result = cache.get(cacheKeyResolver.resolve(invocation.getMethod(),
                        invocation.getArguments()));
                if (null != result) {
                    return NULL_OBJECT == result ? null : result;
                }

                try {
                    result = invocation.proceed();
                } catch (Throwable throwable) {
                    throw wrapException(throwable);
                }

                cache.put(cacheKeyResolver.resolve(invocation.getMethod(), invocation
                        .getArguments()), null == result ? NULL_OBJECT : result);

                return result;
            }
        }),

        /**
         * 刷新缓存操作
         */
        CACHE_CONTEXT_EVENT(new InvokeStrategy() {
            @Override
            public Object invoke(MethodInvocation invocation, IDispatchableCacheContext cacheContext,
                                 ICacheKeyResolver cacheKeyResolver) {
                cacheContext.fireCacheContextEvenet(buildCacheContextEvent
                        (invocation));

                try {
                    return invocation.proceed();
                } catch (Throwable throwable) {
                    throw wrapException(throwable);
                }
            }
        }),

        /**
         * 正常操作
         */
        NORMAL(new InvokeStrategy() {
            @Override
            public Object invoke(MethodInvocation invocation, IDispatchableCacheContext cacheContext,
                                 ICacheKeyResolver cacheKeyResolver) {
                try {
                    return invocation.proceed();
                } catch (Throwable throwable) {
                    throw wrapException(throwable);
                }
            }
        });

        private InvokeStrategy invokeStrategy;

        MethodType(InvokeStrategy invokeStrategy) {
            this.invokeStrategy = invokeStrategy;
        }
    }

    private static ICacheContextEvent buildCacheContextEvent(MethodInvocation invocation) {
        return new DefaultCacheContextEvent(AnnotationUtils.findMetaAnnotaion(invocation.getMethod()
                , CacheContextEvent.class).eventType(), invocation.getThis().getClass(),
                invocation.getMethod(), invocation.getThis(), invocation.getArguments(),
                AnnotationUtils.findAnnotation(invocation.getMethod(), CacheContextEvent.class));
    }

    /**
     * 包装异常
     *
     * @param throwable 异常
     * @return 运行时异常
     */
    private static RuntimeException wrapException(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        else {
            return new RuntimeException(throwable);
        }
    }

}
