package org.cacheframework.context;

/**
 * @Dscription： 缓存环境监听器
 * @Author: zhangyan
 * @Date:2016/12/13.15:28
 * @Version：V1.0
 */
public interface ICacheContextListener {

    /**
     * 缓存上下文事件
     * @param cacheContextEvent 缓存上下文事件
     */
    void onEvent(ICacheContextEvent cacheContextEvent);

}
