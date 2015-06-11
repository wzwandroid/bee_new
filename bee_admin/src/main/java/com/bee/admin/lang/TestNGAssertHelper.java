/*
*/
package com.bee.admin.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于辅助在测试时对两个对象的内容进行比较，该类会提取类中的属性进行一一比较，
 * 而且可以非常清晰地知道是哪个属性导致的问题。解决在进行对象比较时需要手动的对每个属性依次对比。
 * <p>
 * 本类支持属性的类型不是JDK内置的类判断，意思也就是说当类的属性存在自定义类时，可能自定义类没有实现{@code equals}和{@code hashCode}方法时，
 * {@code equals}方法执行的是地址判断。只要在参数{@code propertyClasses}指定哪些类是自定义实体类。
 * </p>
 * 该类主要用于在类没有重写equals和hashCode方法非常有用，当然如果您重写了也可以调用该方法.
 * Created by jiankangjin on 2014/5/8.
 */
@SuppressWarnings("unchecked")
public abstract class TestNGAssertHelper {

    public static final Character OPENING_CHARACTER = '[';
    public static final Character CLOSING_CHARACTER = ']';

    public static final String ASSERT_LEFT = "expected " + OPENING_CHARACTER;
    public static final String ASSERT_LEFT2 = "expected not same " + OPENING_CHARACTER;
    public static final String ASSERT_MIDDLE = CLOSING_CHARACTER + " but found " + OPENING_CHARACTER;
    public static final String ASSERT_RIGHT = Character.toString(CLOSING_CHARACTER);

    /**
     * 判断两个对象的内容是否相同
     * @param actual 时机得到的
     * @param expected 期望得到的
     * @param <T> 泛型类型
     */
    public static <T> void assertEquals(T actual,T expected) {
        if (expected != null && actual == null) {
            throw new AssertionError("It cannot be compared,actual is null,but expected is not null!");
        }
        if (expected == null && actual != null) {
            throw new AssertionError("It cannot be compared,expected is null,but actual is not null!");
        }
        //不考虑数组的关系

        if (expected.equals(actual)) {
            return;
        }
        Class clazz = expected.getClass();
        Field[] fields = getFields(clazz);
        for (Field field : fields) {
            String methodName = "get".concat(StringUtils.capitalize(field.getName()));
            try {
                Method method = clazz.getMethod(methodName);
                Class returnType = method.getReturnType();
                Object val1 = method.invoke(actual);
                Object val2 = method.invoke(expected);
                if (!val1.equals(val2)) {
                    String msg = String.format("When field '%s' compared,the field of actual value is %s,and the field of expected value is %s.", field.getName(), val1, val2);
                    failNotEquals(val1,val2,msg);
                    return;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断两个对象内容是否相同,支持属性非JDK内置类
     * <p>
     *     举例说明，假设类Person有四个属性：id,username,passwd,group，其中group的类型是Group，类Group只有两个属性id，name。
     *     如果此时把Person的两个对象实例进行断言判断时因为存在Group不确定性，所以需要对Group对象值也同样和类Person比较。
     *     TestNGAssertHelper.assertEquals(p1,p2,Group.class);
     * </p>
     * @param actual 实际得到的
     * @param expected 期望得到的
     * @param propertyClasses 排除哪些类是需要自定义判断比较的
     * @param <T>
     */
    public static <T> void assertEquals(T actual, T expected, Class... propertyClasses) {
        if (expected != null && actual == null) {
            throw new AssertionError("It cannot be compared,actual is null,but expected is not null!");
        }
        if (expected == null && actual != null) {
            throw new AssertionError("It cannot be compared,expected is null,but actual is not null!");
        }
        //不考虑数组的关系

        if (expected.equals(actual)) {
            return;
        }
        Class clazz = expected.getClass();
        Field[] fields = getFields(clazz);
        for (Field field : fields) {
            String methodName = "get".concat(StringUtils.capitalize(field.getName()));
            try {
                Method method = clazz.getMethod(methodName);
                Class returnType = method.getReturnType();
                Object val1 = method.invoke(actual);
                Object val2 = method.invoke(expected);
                if (ObjectUtils.containsElement(propertyClasses, returnType)) {
                    assertEquals(val1,val2,propertyClasses);
                } else {
                    if (!val1.equals(val2)) {
                        String msg = String.format("When field[%s.%s] compared,the field of actual value is %s,and the field of expected value is %s.",
                                clazz.getName(), field.getName(), val1, val2);
                        //throw new AssertionError(msg);
                        failNotEquals(val1,val2,msg);
                        return;
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static Field[] getFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> list = new ArrayList<Field>(fields.length);
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if (!Modifier.isStatic(modifier)) {
                list.add(field);
            }

        }
        Field[] fieldArr = new Field[list.size()];
        return list.toArray(fieldArr);
    }

    static private void failNotEquals(Object actual , Object expected, String message ) {
        fail(format(actual, expected, message));
    }

    /**
     * Fails a test with the given message.
     * @param message the assertion error message
     */
    static public void fail(String message) {
        throw new AssertionError(message);
    }

    static String format(Object actual, Object expected, String message) {
        String formatted = "";
        if (null != message) {
            formatted = message + " ";
        }
        return formatted + ASSERT_LEFT + expected + ASSERT_MIDDLE + actual + ASSERT_RIGHT;
    }
}
