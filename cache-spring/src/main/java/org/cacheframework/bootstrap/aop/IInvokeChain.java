package org.cacheframework.bootstrap.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @Dscription： 调用链
 * @Author: zhangyan
 * @Date:2016/12/18.21:37
 * @Version：V1.0
 */
public interface IInvokeChain {

    /**
     * 调用下个调用链
     * @return 返回结果
     */
    Object invokeNext() throws Throwable;

    /**
     * 获取方法调用
     * @return 方法调用
     */
    MethodInvocation getInvocation();

}
