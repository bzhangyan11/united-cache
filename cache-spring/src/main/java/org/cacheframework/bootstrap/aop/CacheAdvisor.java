package org.cacheframework.bootstrap.aop;


import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.context.IDispatchableCacheContext;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @Dscription： 缓存切面
 * @Author: zhangyan
 * @Date:2016/11/30.11:11
 * @Version：V1.0
 */
public class CacheAdvisor extends DefaultPointcutAdvisor {

    private final Pointcut cacheAnnotationPointcut = new CacheAnnotationPointcut();

    private final Advice cacheAnnotationMethodInterceptor = new CacheAnnotationMethodInterceptor();

    private final IMatchingMethodStrategy matchMethodStrategy;

    private final IInvoker invoker;

    private final IDispatchableCacheContext dispatchableCacheContext;

    @Override
    public Pointcut getPointcut() {
        return this.cacheAnnotationPointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.cacheAnnotationMethodInterceptor;
    }

    public CacheAdvisor(IMatchingMethodStrategy matchMethodStrategy, IInvoker invoker,
                        IDispatchableCacheContext dispatchableCacheContext){
        this.matchMethodStrategy = matchMethodStrategy;
        this.invoker = invoker;
        this.dispatchableCacheContext = dispatchableCacheContext;
    }

    /**
     * 执行匹配操作
     * @param method 方法
     * @param targetClass 目标类
     * @return 是否匹配
     */
    protected boolean doMatchs(Method method, Class<?> targetClass){
        if(null == this.matchMethodStrategy){
            throw new IllegalArgumentException("matching strategy is null");
        }

        return this.matchMethodStrategy.match(method,targetClass);
    }

    /**
     * 执行调用
     * @param invocation 方法调用
     * @return 返回值
     */
    protected Object doInvoke(MethodInvocation invocation){
        return this.invoker.invoke(invocation,this.dispatchableCacheContext);
    }

    /**
     * 缓存注解切点
     */
    private class CacheAnnotationPointcut extends StaticMethodMatcherPointcut {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return CacheAdvisor.this.doMatchs(method,targetClass);
        }
    }

    /**
     * 缓存注解方法拦截器
     */
    private class CacheAnnotationMethodInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            return CacheAdvisor.this.doInvoke(invocation);
        }
    }

}
