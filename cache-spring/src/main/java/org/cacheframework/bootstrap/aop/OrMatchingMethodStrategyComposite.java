package org.cacheframework.bootstrap.aop;


import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Dscription： or 匹配策略组合器
 * @Author: zhangyan
 * @Date:2016/12/13.15:59
 * @Version：V1.0
 */
public class OrMatchingMethodStrategyComposite implements IMatchingMethodStrategy {

    private final List<IMatchingMethodStrategy> matchingMethodStrategies;

    public OrMatchingMethodStrategyComposite(List<IMatchingMethodStrategy> matchingMethodStrategies){
        this.matchingMethodStrategies = matchingMethodStrategies;
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
        List<IMatchingMethodStrategy> matchingMethodStrategies = this.matchingMethodStrategies;
        if (CollectionUtils.isEmpty(matchingMethodStrategies)) {
            throw new IllegalArgumentException("matching strategy is empty");
        }

        for (IMatchingMethodStrategy matchingMethodStrategy : matchingMethodStrategies) {
            boolean isMatched = matchingMethodStrategy.match(method, clz);
            if (isMatched) {
                return true;
            }
        }

        return false;
    }
}
