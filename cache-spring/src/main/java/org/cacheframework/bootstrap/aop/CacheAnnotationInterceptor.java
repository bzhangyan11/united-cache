package org.cacheframework.bootstrap.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.cache.ICache;
import org.cacheframework.context.ICacheContext;
import org.cacheframework.context.ICacheKeyResolver;
import org.cacheframework.utils.AnnotationUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @Dscription： 缓存注解拦截器
 * @Author: zhangyan
 * @Date:2016/12/18.22:00
 * @Version：V1.0
 */
@Order(20)
public class CacheAnnotationInterceptor implements ICacheInterceptor,IMatchingMethodStrategy{

    private static final Object NULL_OBJECT = new Object() {
        @Override
        public String toString() {
            return super.toString() + "null cache object";
        }
    };

    private final ICacheKeyResolver cacheKeyResolver;

    public CacheAnnotationInterceptor(ICacheKeyResolver cacheKeyResolver) {
        this.cacheKeyResolver = cacheKeyResolver;
    }

    /**
     * 调用
     *
     * @param invokeChain 调用链
     * @return 返回结果
     */
    @Override
    public Object invoke(IInvokeChain invokeChain, ICacheContext cacheContext) throws Throwable {
        ICache cache = cacheContext.getCache(invokeChain.getInvocation().getMethod());

        Object key = cacheKeyResolver.resolve(invokeChain.getInvocation().getMethod(),
                invokeChain.getInvocation().getArguments());

        Object result = null;
        if (null != key) {
            result = cache.get(key);
        }

        if (null != result) {
            return NULL_OBJECT == result ? null : result;
        }

        result = invokeChain.getInvocation().proceed();

        if (null != key) {
            cache.put(cacheKeyResolver.resolve(invokeChain.getInvocation().getMethod(),
                    invokeChain.getInvocation().getArguments()), null == result ? NULL_OBJECT :
                    result);
        }

        return result;
    }

    /**
     * 是否匹配方法调用
     *
     * @param methodInvocation 方法调用
     * @return 是否匹配
     */
    @Override
    public boolean match(MethodInvocation methodInvocation) {
        return this.match(methodInvocation.getMethod(),methodInvocation.getThis().getClass());
    }

    /**
     * 方法是否匹配
     *
     * @param method 方法
     * @param clz    类
     * @return 是否匹配
     */
    @Override
    public boolean match(Method method, Class<?> clz) {
        return null != AnnotationUtils.findCacheMetaAnnotation(method) && void.class != method
                .getReturnType();
    }
}
