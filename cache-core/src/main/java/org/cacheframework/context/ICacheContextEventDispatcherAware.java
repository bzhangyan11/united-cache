package org.cacheframework.context;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/14.13:54
 * @Version：V1.0
 */
public interface ICacheContextEventDispatcherAware {

    /**
     * 设置缓存上下文事件派发器
     * @param cacheContextEventDispatcher 缓存上下文事件派发器
     */
    void setCacheContextEventDispatcher(ICacheContextEventDispatcher
                                                cacheContextEventDispatcher);

}
