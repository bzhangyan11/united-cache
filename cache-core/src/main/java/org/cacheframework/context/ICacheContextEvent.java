package org.cacheframework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Dscription：
 * @Author: zhangyan
 * @Date:2016/12/13.15:25
 * @Version：V1.0
 */
public interface ICacheContextEvent<T extends Enum<T>, A extends Annotation> {

    /**
     * 获得事件枚举类型
     *
     * @return
     */
    Class<? extends IEventType> getEventType();

    /**
     * 获取事件来源类
     *
     * @return
     */
    Class<?> getSourceClass();

    /**
     * 获取事件来源方法
     *
     * @return
     */
    Method getSourceMethod();

    /**
     * 获取来源
     *
     * @return
     */
    Object getSource();

    /**
     * 获取方法参数
     *
     * @return
     */
    Object[] getArgs();

    /**
     * 获取事件触发枚举
     *
     * @return
     */
    A getAnnotation();

}
