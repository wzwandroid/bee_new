/*
*/
package com.bee.admin.lang;


import com.bee.admin.utils.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Assert类主要用于校验参数.
 * Created by jiankangjin on 2014/4/24.
 */
public abstract class Assert {

    /**
     * 如果布尔表达式值是{@code false}则抛出异常{@code IllegalArgumentException}
     * <pre class="code">
     * Assert.isTrue(i<0,"The value must be greater than zero");
     * </pre>
     *
     * @param expression 布尔表达式
     * @param message    异常信息
     * @throws IllegalArgumentException
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 如果布尔表达式值是{@code false}则抛出异常{@code IllegalArgumentException}
     * <pre class="code">
     * Assert.isTrue(i<0);
     * </pre>
     *
     * @param expression 布尔表达式
     * @throws IllegalArgumentException
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * 校验对象是否为{@code null},如果对象不是{@code null}则抛出异常{@code IllegalArgumentException}
     * <pre class="code">
     * Assert.isNull(value,"The value must be null");
     * </pre>
     *
     * @param object  对象
     * @param message 异常信息
     * @throws IllegalArgumentException
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验对象是否为{@code null},如果对象不是{@code null}则抛出异常{@code IllegalArgumentException}
     * <pre class="code">Assert.isNull(value);</pre>
     *
     * @param object 对象
     * @throws IllegalArgumentException
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * 校验对象是否为{@code null},如果对象是{@code null}则抛出异常{@code IllegalArgumentException}
     * <pre class="code">
     * Assert.isNotNull(clazz,"The class must not be null");
     * </pre>
     *
     * @param obj     对象
     * @param message 异常信息
     * @throws IllegalArgumentException
     */
    public static void isNotNull(Object obj, String message) {
        if (null == obj)
            throw new IllegalArgumentException(message);
    }

    /**
     * 校验对象是否为{@code null},如果对象是{@code null}则抛出异常{@code IllegalArgumentException}
     * <pre class="code">
     * Assert.isNotNull(str);
     * </pre>
     *
     * @param obj 对象
     * @throws IllegalArgumentException
     */
    public static void isNotNull(Object obj) {
        isNotNull(obj, "[Assertion failed] -- this object argument must not be null.");
    }

    /**
     * 校验字符串是否为空：既不为{@code null}也不为空字符串
     * <pre class="code">
     * Assert.hasLength(str,"str must not be empty");
     * </pre>
     *
     * @param text    字符串
     * @param message 异常信息
     * @throws IllegalArgumentException
     */
    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验字符串是否为空：既不为{@code null}也不为空字符串
     * <pre class="code">
     * Assert.hasLength(str);
     * </pre>
     *
     * @param text 字符串
     * @throws IllegalArgumentException
     * @see #hasLength(String, String)
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length;that is,it must not be null or empty");
    }

    /**
     * 校验字符串是否存在文本内容
     * <pre class="code">
     * Assert.hasText((username, "'username' must not be empty");
     * </pre>
     *
     * @param text    字符串
     * @param message 异常信息
     * @throws IllegalArgumentException
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验字符串是否存在文本内容
     * <pre class="code">
     * Assert.hasText((username);
     * </pre>
     *
     * @param text 字符串
     * @throws IllegalArgumentException
     * @see #hasText(String, String)
     */
    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * 校验字符串中不能含有子串{@code substring}
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     *
     * @param textToSearch 字符串
     * @param substring    不应该包含的字符串
     * @param message      异常信息
     * @throws IllegalArgumentException
     * @since jdk1.5
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验字符串中不能含有子串{@code substring}
     * <pre class="code">Assert.hasText(name);</pre>
     *
     * @param textToSearch 字符串
     * @param substring    不应该包含的字符串
     * @throws IllegalArgumentException
     * @since jdk1.5
     */
    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring,
                "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    /**
     * 校验数组是否为空
     * <pre class="code">
     * Assert.notEmpty(array, "The array must have elements");
     * </pre>
     *
     * @param array   数组
     * @param message 异常信息
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验数组是否为空
     * <pre class="code">
     * Assert.notEmpty(array);
     * </pre>
     *
     * @param array 数组
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * 校验集合对象是否为空
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     *
     * @param collection 集合对象
     * @param message    异常信息
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验集合对象是否为空
     * <pre class="code">Assert.notEmpty(collection);</pre>
     *
     * @param collection 集合对象
     * @throws IllegalArgumentException
     * @see #notEmpty(java.util.Collection, String)
     */
    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection,
                "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * 校验Map对象是否为空
     * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
     *
     * @param map     map对象
     * @param message 异常信息
     * @throws IllegalArgumentException
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 校验Map对象是否为空
     * <pre class="code">Assert.notEmpty(map);</pre>
     *
     * @param map map对象
     * @throws IllegalArgumentException
     * @see #notEmpty(java.util.Map, String)
     */
    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }
}
