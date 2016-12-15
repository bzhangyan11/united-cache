package org.cacheframework.utils;


import org.apache.commons.lang3.ArrayUtils;
import org.cacheframework.annotation.Cache;
import org.cacheframework.cache.GlobalSoftCache;
import org.springframework.core.BridgeMethodResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Dscription： 注解工具
 * @Author: zhangyan
 * @Date:2016/12/13.14:17
 * @Version：V1.0
 */
public class AnnotationUtils {

    private static final Annotation NONE_ANNOTAION = new NoneAnnotation(){

        @Override
        public String toString() {
            return "none annotation cache";
        }
    };

    private static final FindResultCache FIND_ANNOTATION_CACHE = new
            FindResultCache();

    private static final FindResultCache FIND_META_ANNOTATION_CACHE =
            new FindResultCache();

    /**
     * 寻找有缓存元注解的注解信息
     *
     * @param method             方法
     * @param metaAnnotationType 元注解类型
     * @return 注解信息
     */
    public static Annotation findAnnotation(Method method, Class<? extends Annotation>
            metaAnnotationType) {
        if(null == method || null == metaAnnotationType){
            throw new IllegalArgumentException("method or annotation type can't be null;");
        }

        Annotation annotation = FIND_ANNOTATION_CACHE.getCache(getCacheKey(method, metaAnnotationType));
        if (null != annotation) {
            return unwrapAnnotation(annotation);
        }

        Method originMethod = BridgeMethodResolver.findBridgedMethod(method);

        annotation = originMethod.getAnnotation(metaAnnotationType);
        if (null != annotation) {
            FIND_ANNOTATION_CACHE.putCache(getCacheKey(method, metaAnnotationType), annotation);
            return annotation;
        }

        Annotation[] allAnnotations = originMethod.getAnnotations();
        if (ArrayUtils.isEmpty(allAnnotations)) {
            FIND_ANNOTATION_CACHE.putCache(getCacheKey(method, metaAnnotationType), NONE_ANNOTAION);
            return null;
        }

        for (Annotation anno : allAnnotations) {
            Annotation cacheAnnotation = anno.annotationType().getAnnotation(metaAnnotationType);
            if (null != cacheAnnotation) {
                FIND_ANNOTATION_CACHE.putCache(getCacheKey(method, metaAnnotationType), anno);
                return anno;
            }
        }

        FIND_ANNOTATION_CACHE.putCache(getCacheKey(method, metaAnnotationType), NONE_ANNOTAION);
        return null;
    }

    /**
     * 寻找缓存注解信息
     *
     * @param method 方法
     * @return 注解信息
     */
    public static Annotation findCacheAnnotation(Method method) {
        return findAnnotation(method, Cache.class);
    }

    /**
     * 根据提供的注解类型寻找相应的缓存元数据信息
     *
     * @param method         方法
     * @param annotationType 注解类型
     * @param <T>            类型
     * @return 注解信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T findMetaAnnotaion(Method method, Class<T>
            annotationType) {
        if(null == method || null == annotationType){
            throw new IllegalArgumentException("method or annotation type can't be null;");
        }

        Annotation annotation = FIND_META_ANNOTATION_CACHE.getCache(getCacheKey(method,
                annotationType));
        if (null != annotation) {
            return (T) unwrapAnnotation(annotation);
        }

        annotation = org.springframework.core.annotation.AnnotationUtils.getAnnotation(method,
                annotationType);
        if (null == annotation) {
            FIND_META_ANNOTATION_CACHE.putCache(getCacheKey(method, annotationType),
                    NONE_ANNOTAION);
        }
        else {
            FIND_META_ANNOTATION_CACHE.putCache(getCacheKey(method, annotationType), annotation);
        }

        return (T) annotation;
    }

    /**
     * 寻找缓存元注解信息
     *
     * @param method 方法
     * @return 元注解信息
     */
    public static Cache findCacheMetaAnnotation(Method method) {
        return findMetaAnnotaion(method, Cache.class);
    }

    private static String getCacheKey(Method method, Class<?> annotationType) {
        return method.getDeclaringClass().getName() + "." + method.getName() + "_" +
                annotationType.getName();
    }

    private static Annotation unwrapAnnotation(Annotation annotation) {
        if (NONE_ANNOTAION == annotation) {
            return null;
        }
        else {
            return annotation;
        }
    }

    /**
     * 空注解
     */
    private static class NoneAnnotation implements Annotation {

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }

    private static class FindResultCache extends GlobalSoftCache {
        /**
         * 放入缓存
         *
         * @param key   键
         * @param value 值
         */
        public void putCache(String key, Annotation value) {
            this.put(key, value);
        }

        /**
         * 获取缓存
         *
         * @param key 键
         * @return 值
         */
        public Annotation getCache(String key) {
            return (Annotation) this.get(key);
        }
    }

}
