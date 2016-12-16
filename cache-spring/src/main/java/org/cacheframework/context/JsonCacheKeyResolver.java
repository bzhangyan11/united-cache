package org.cacheframework.context;

import com.google.gson.Gson;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @Dscription： 使用json处理缓存key
 * @Author: zhangyan
 * @Date:2016/12/13.10:10
 * @Version：V1.0
 */
@Order(40)
public class JsonCacheKeyResolver implements ICacheKeyResolver {

    private final static Gson GSON = new Gson();

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
    public boolean support(Method method) {
        return !ArrayUtils.isEmpty(method.getParameterTypes());
    }

    /**
     * 得出缓存 key
     *
     * @param method 方法
     * @param args   参数
     * @return 缓存key
     */
    @Override
    public Object resolve(Method method, Object[] args) {
        if (!this.support(method)) {
            throw new IllegalArgumentException("unsupported method :" + method);
        }

        boolean isArgsAllNull = true;

        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            if (null == arg) {
                builder.append("N");
            }
            else {
                builder.append(GSON.toJson(arg));
                isArgsAllNull = false;
            }
        }

        return isArgsAllNull ? null : builder.toString();
    }
}
