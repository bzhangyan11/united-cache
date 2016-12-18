package org.cacheframework.bootstrap.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.context.ICacheContext;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/18.22:19
 * @Version：V1.0
 */
public interface ICacheInterceptor {

    /**
     * 调用
     *
     * @param invokeChain 调用链
     * @return 返回结果
     */
    Object invoke(ICacheInterceptorChain invokeChain, ICacheContext cacheContext) throws Throwable;

    /**
     * 是否匹配方法调用
     *
     * @param methodInvocation 方法调用
     * @return 是否匹配
     */
    boolean match(MethodInvocation methodInvocation);


}
