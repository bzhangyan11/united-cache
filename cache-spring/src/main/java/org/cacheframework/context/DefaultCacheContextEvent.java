package org.cacheframework.context;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Dscription： 缓存上下文事件
 * @Author: zhangyan
 * @Date:2016/12/13.18:49
 * @Version：V1.0
 */
public class DefaultCacheContextEvent implements ICacheContextEvent {

    private Class<? extends IEventType> eventType;

    private Class<?> sourceClass;

    private Method sourceMethod;

    private Object source;

    private Object[] args;

    private Annotation annotation;

    public DefaultCacheContextEvent(Class<? extends IEventType> eventType, Class<?> sourceClass, Method sourceMethod,
                                    Object source, Object[] args, Annotation annotation) {
        this.eventType = eventType;
        this.sourceClass = sourceClass;
        this.sourceMethod = sourceMethod;
        this.source = source;
        this.args = args;
        this.annotation = annotation;
    }

    /**
     * 获得事件枚举类型
     *
     * @return
     */
    public Class<? extends IEventType> getEventType() {
        return this.eventType;
    }

    /**
     * 获取事件来源类
     *
     * @return
     */
    @Override
    public Class<?> getSourceClass() {
        return this.sourceClass;
    }

    /**
     * 获取事件来源方法
     *
     * @return
     */
    @Override
    public Method getSourceMethod() {
        return this.sourceMethod;
    }

    /**
     * 获取来源
     *
     * @return
     */
    @Override
    public Object getSource() {
        return this.source;
    }

    /**
     * 获取方法参数
     *
     * @return
     */
    @Override
    public Object[] getArgs() {
        return this.args;
    }

    /**
     * 获取事件触发枚举
     *
     * @return
     */
    @Override
    public Annotation getAnnotation() {
        return this.annotation;
    }
}
