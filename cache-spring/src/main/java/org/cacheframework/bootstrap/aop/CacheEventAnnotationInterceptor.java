package org.cacheframework.bootstrap.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.annotation.CacheContextEvent;
import org.cacheframework.context.DefaultCacheContextEvent;
import org.cacheframework.context.ICacheContext;
import org.cacheframework.context.ICacheContextEvent;
import org.cacheframework.context.IDispatchableCacheContext;
import org.cacheframework.utils.AnnotationUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @Dscription： 缓存事件注解拦截器
 * @Author: zhangyan
 * @Date:2016/12/18.22:10
 * @Version：V1.0
 */
@Order(10)
public class CacheEventAnnotationInterceptor implements ICacheInterceptor,IMatchingMethodStrategy {

    /**
     * 调用
     *
     * @param invokeChain 调用链
     * @return 返回结果
     */
    @Override
    public Object invoke(ICacheInterceptorChain invokeChain, ICacheContext cacheContext) throws Throwable {
        if(cacheContext instanceof IDispatchableCacheContext){
            ((IDispatchableCacheContext) cacheContext).fireCacheContextEvenet(buildCacheContextEvent
                    (invokeChain.getInvocation()));
        }

        return invokeChain.invokeNext();

    }

    /**
     * 构建缓存事件对象
     * @param invocation 调用对象
     * @return 缓存事件对象
     */
    private ICacheContextEvent buildCacheContextEvent(MethodInvocation invocation) {
        return new DefaultCacheContextEvent(AnnotationUtils.findMetaAnnotaion(invocation.getMethod()
                , CacheContextEvent.class).eventType(), invocation.getThis().getClass(),
                invocation.getMethod(), invocation.getThis(), invocation.getArguments(),
                AnnotationUtils.findAnnotation(invocation.getMethod(), CacheContextEvent.class));
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
        return null != AnnotationUtils.findMetaAnnotaion(method,
                CacheContextEvent.class);
    }
}
