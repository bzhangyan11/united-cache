package org.cacheframework.bootstrap.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.cacheframework.context.ICacheContext;

import java.util.Collections;
import java.util.List;

/**
 * @Dscription： 默认调用链
 * @Author: zhangyan
 * @Date:2016/12/18.21:42
 * @Version：V1.0
 */
public class DefaultInvokeChain implements IInvokeChain {

    private final List<ICacheInterceptor> invokeFilters;

    private final MethodInvocation methodInvocation;

    private final ICacheContext cacheContext;

    private int index = 0;

    public DefaultInvokeChain(List<ICacheInterceptor> invokeFilters, MethodInvocation
            methodInvocation, ICacheContext cacheContext) {
        this.invokeFilters = Collections.unmodifiableList(invokeFilters);
        this.methodInvocation = methodInvocation;
        this.cacheContext = cacheContext;
    }

    /**
     * 调用下个调用链
     *
     * @return 返回结果
     */
    @Override
    public Object invokeNext() throws Throwable {
        return this.invokeFilters.get(index++).invoke(this, cacheContext);
    }

    /**
     * 获取方法调用
     *
     * @return 方法调用
     */
    @Override
    public MethodInvocation getInvocation() {
        return methodInvocation;
    }
}
