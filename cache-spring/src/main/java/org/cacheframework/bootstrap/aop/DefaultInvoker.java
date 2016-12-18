package org.cacheframework.bootstrap.aop;


import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.context.*;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 默认调用器
 * @Author: zhangyan
 * @Date:2016/12/13.11:26
 * @Version：V1.0
 */
public class DefaultInvoker implements IInvoker {

    private final List<ICacheInterceptor> allSupportedInterceptor;

    private final ConcurrentMap<Method, List<ICacheInterceptor>> cacheInterceptorCache = new
            ConcurrentHashMap<>();

    public DefaultInvoker(List<ICacheInterceptor> allSupportedInterceptor) {
        this.allSupportedInterceptor = Collections.unmodifiableList(allSupportedInterceptor);
    }

    /**
     * 调用方法逻辑
     *
     * @param invocation 方法调用
     * @return 返回值
     */
    @Override
    public Object invoke(MethodInvocation invocation, ICacheContext cacheContext) {
        try {
            return this.buildInvokeChain(invocation, cacheContext).invokeNext();
        } catch (Throwable throwable) {
            throw this.wrapException(throwable);
        }
    }


    /**
     * 包装异常
     *
     * @param throwable 异常
     * @return 运行时异常
     */
    private RuntimeException wrapException(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        }
        else {
            return new RuntimeException(throwable);
        }
    }

    /**
     * 构造调用链
     *
     * @param invocation 方法调用
     * @return 调用链
     */
    private ICacheInterceptorChain buildInvokeChain(MethodInvocation invocation, ICacheContext cacheContext) {
        List<ICacheInterceptor> cacheInterceptors = this.cacheInterceptorCache.get(invocation.getMethod());

        if (null == cacheInterceptors) {
            cacheInterceptors = this.getAndSortMethodSupportedInterceptors(invocation);
            this.cacheInterceptorCache.putIfAbsent(invocation.getMethod(), cacheInterceptors);
        }

        return new DefaultCacheInterceptorChain(cacheInterceptors, invocation, cacheContext);
    }

    /**
     * 获取并排序方法支持的拦截器列表
     *
     * @param invocation 方法
     * @return 拦截器列表
     */
    private List<ICacheInterceptor> getAndSortMethodSupportedInterceptors(MethodInvocation
                                                                                  invocation) {
        List<ICacheInterceptor> methodSupportedInvocations = new ArrayList<>();
        for (ICacheInterceptor cacheInterceptor : allSupportedInterceptor) {
            if (cacheInterceptor.match(invocation)) {
                methodSupportedInvocations.add(cacheInterceptor);
            }
        }

        Collections.sort(methodSupportedInvocations, AnnotationAwareOrderComparator.INSTANCE);

        methodSupportedInvocations.add(new OriginMethodInvocation());

        return methodSupportedInvocations;
    }

    /**
     * 原始方法调用
     */
    private static class OriginMethodInvocation implements ICacheInterceptor {

        /**
         * 调用
         *
         * @param invokeChain 调用链
         * @return 返回结果
         */
        @Override
        public Object invoke(ICacheInterceptorChain invokeChain, ICacheContext cacheContext) throws Throwable {
            return invokeChain.getInvocation().proceed();
        }

        /**
         * 是否匹配方法调用
         *
         * @param methodInvocation 方法调用
         * @return 是否匹配
         */
        @Override
        public boolean match(MethodInvocation methodInvocation) {
            return true;
        }
    }

}
