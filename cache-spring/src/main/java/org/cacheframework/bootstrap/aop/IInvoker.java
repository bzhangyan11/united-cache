package org.cacheframework.bootstrap.aop;


import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.context.IDispatchableCacheContext;

/**
 * @Dscription： 调用接口
 * @Author: zhangyan
 * @Date:2016/12/13.11:16
 * @Version：V1.0
 */
public interface IInvoker {

    /**
     * 调用方法逻辑
     *
     * @param invocation 方法调用
     * @return 返回值
     */
    Object invoke(MethodInvocation invocation, IDispatchableCacheContext cacheContext);

}
