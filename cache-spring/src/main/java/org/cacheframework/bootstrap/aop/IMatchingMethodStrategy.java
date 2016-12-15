package org.cacheframework.bootstrap.aop;

import java.lang.reflect.Method;

/**
 * @Dscription： 方法是否匹配策略
 * @Author: zhangyan
 * @Date:2016/12/13.10:59
 * @Version：V1.0
 */
public interface IMatchingMethodStrategy {

    /**
     * 方法是否匹配
     *
     * @param method 方法
     * @param clz    类
     * @return 是否匹配
     */
    boolean match(Method method, Class<?> clz);

}
