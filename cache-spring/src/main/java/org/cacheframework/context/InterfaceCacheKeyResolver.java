package org.cacheframework.context;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Dscription： 接口 key 缓存 解析器
 * @Author: zhangyan
 * @Date:2016/12/13.10:09
 * @Version：V1.0
 */
@Order(20)
public class InterfaceCacheKeyResolver implements ICacheKeyResolver {

    private static final Integer NULL_INDEX = -1;

    private final ConcurrentMap<Method, Integer> methodToArgsIndexCacheMap = new
            ConcurrentHashMap<>();

    /**
     * 是否启用该缓存处理器
     *
     * @return 是否启用
     */
    @Override
    public boolean enable() {
        return true;
    }

    /**
     * 此缓存key处理器是否支持此方法
     *
     * @param method 方法
     * @return 是否支持
     */
    @Override
    public boolean support(Method method, Class<?> targetClz) {
        Integer index = this.getIndexOfFirstSupportedParam(method);
        return null != this.unwrapIndex(index);
    }

    /**
     * 得出缓存 key
     *
     * @param method 方法
     * @param args   参数
     * @return 缓存key
     */
    @Override
    public Object resolve(Method method, Class<?> targetClz, Object[] args) {
        Integer index = this.getIndexOfFirstSupportedParam(method);

        index = this.unwrapIndex(index);

        if (index != null) {
            return null == args[index] ? null : ((ICacheKey) args[index]).key();
        }
        else {
            throw new UnsupportedOperationException("method: " + method.getName() + " is " +
                    "Unsupported for this cache key resolver");
        }
    }

    private Integer unwrapIndex(Integer index) {
        if (NULL_INDEX.equals(index)) {
            return null;
        }

        return index;
    }

    /**
     * 获取第一个支持此解析器的方法参数索引号
     *
     * @param method 方法
     * @return 索引号
     */
    private Integer getIndexOfFirstSupportedParam(Method method) {
        Integer index = methodToArgsIndexCacheMap.get(method);
        if (null != index) {
            return index;
        }

        index = NULL_INDEX;
        Class<?>[] classes = method.getParameterTypes();
        if (!ArrayUtils.isEmpty(classes)) {
            for (int i = 0; i < classes.length; i++) {
                if (ICacheKey.class.isAssignableFrom(classes[i])) {
                    index = i;
                    break;
                }
            }
        }

        methodToArgsIndexCacheMap.putIfAbsent(method, index);

        return index;
    }
}
