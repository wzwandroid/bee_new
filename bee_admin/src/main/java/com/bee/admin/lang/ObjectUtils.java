/*
*/
package com.bee.admin.lang;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Object类的常用方法和以及对数组操作的方法
 * Created by jiankangjin on 2014/4/24.
 */
public abstract class ObjectUtils {
    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;

    /**
     * 判断异常类型是否是检查类型异常，当既不是{@code RuntimeException}也不是{@code Error}时则就是检查类型异常.
     * <p/>
     * <pre class="code">
     * IO
     * ObjectUtils.isCheckedException(new IOException()); //---> true
     * ObjectUtils.isCheckedException(new NullPointerException()); //---> false
     * </pre>
     *
     * @param ex 异常
     * @return 如果是检查类型异常则返回{@code true},否则返回{@code false}
     */
    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }

    /**
     * 判断是否是数组类型
     * <p/>
     * <pre class="code">
     * ObjectUtils.isArray(null); //---> false;
     * ObjectUtils.isArray(new String[]{"aa","bb"}); //---> true
     * </pre>
     *
     * @param obj 对象
     * @return 如果是数组类型则返回{@code true},否则返回{@code false}
     */
    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    /**
     * 判断数组是否是空数组
     * <p/>
     * <pre class="code">
     * ObjectUtils.isEmpty(new String[]{"aa","bb"}); //---> false
     * ObjectUtils.isEmpty(new String[]{ }); //---> true
     * </pre>
     *
     * @param array 对象数组
     * @return 如果数组长度不为0则返回{@code false},否则返回{@code true}
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 判断数组是否不是空数组
     * <p/>
     * <pre class="code">
     * ObjectUtils.isNotEmpty(new String[]{"aa","bb"}); //---> true
     * ObjectUtils.isNotEmpty(new String[]{ }); //---> false
     * </pre>
     *
     * @param array 对象数组
     * @return 如果数组长度不为0则返回{@code true},否则返回{@code false}
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断数组中是否包含了指定的元素
     * <p/>
     * <pre class="code">
     * ObjectUtils.containsElement(new String[]{"aaaa","bbb","cc",null},null); //---> true
     * ObjectUtils.containsElement(new String[]{"aaaa","bbb","cc"},"cc"); //---> true
     * ObjectUtils.containsElement(new String[]{"aaaa","bbb","cc",null},"xx"); //---> false
     * </pre>
     *
     * @param array   数组
     * @param element 检查的元素对象
     * @return 如果数组中存在则返回{@code true},否则返回{@code false}
     * @see #nullSafeEquals(Object, Object)
     */
    public static boolean containsElement(Object[] array, Object element) {
        if (isEmpty(array)) {
            return false;
        }
        for (Object arrayEle : array) {
            if (nullSafeEquals(arrayEle, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断枚举数组里是否包含了指定的枚举名,枚举的比较是通过枚举名字比较.枚举值不考虑大小写问题.
     * <p/>
     * <pre class="code">
     * MyEnum[] myEnums = MyEnum.values();
     * ObjectUtils.containsConstant(myEnums,"Enabled"); //---> true
     * ObjectUtils.containsConstant(myEnums,null); //---> false
     * </pre>
     *
     * @param enumValues 枚举数组，通常可以这样得到MyEnums.values()
     * @param constant   检查的枚举名
     * @return 如果存在则返回{@code true},否则返回{@code false}
     * @see #containsConstant(Enum[], String, boolean)
     */
    public static boolean containsConstant(Enum<?>[] enumValues, String constant) {
        return containsConstant(enumValues, constant, true);
    }

    /**
     * 判断枚举数组里是否包含了指定的枚举名,枚举的比较是通过枚举名字比较.
     * <p/>
     * <pre class="code">
     * ObjectUtils.containsConstant(myEnums,"Enabled",true); //---> true
     * ObjectUtils.containsConstant(myEnums,"Enabled",false); //---> false
     * ObjectUtils.containsConstant(myEnums,null,true); //---> false
     * </pre>
     *
     * @param enumValues    枚举数组，通常可以这样得到MyEnums.values()
     * @param constant      检查的枚举名
     * @param caseSensitive 是否忽略大小写,如果值为{@code true}则忽略大小写,否则不忽略大小写.
     * @return 如果存在则返回{@code true},否则返回{@code false}
     */
    public static boolean containsConstant(Enum<?>[] enumValues, String constant, boolean caseSensitive) {
        if (isEmpty(enumValues) || constant == null) {
            return false;
        }
        for (Enum<?> candidate : enumValues) {
            boolean equals = caseSensitive ? candidate.toString().equalsIgnoreCase(constant)
                    : candidate.toString().equals(constant);
            if (equals) {
                return true;
            }
        }
        return false;
    }

    /**
     * 向数组中追加一个字符串，返回一个新的数组对象.
     * <p/>
     * <pre class="code">
     * ObjectUtils.addObjectToArray(new String[]{"hello"},"world"); //--->[hello, world]
     * </pre>
     *
     * @param array 原始数组
     * @param obj   追加的元素
     * @param <A>   原始数组的泛型类型
     * @param <O>   追加的元素泛型类型
     * @return 返回一个新的数组，该数组永远不为{@code null}
     */
    public static <A, O extends A> A[] addObjectToArray(A[] array, O obj) {
        Class<?> compType = Object.class;
        if (array != null) {
            compType = array.getClass().getComponentType();
        } else if (obj != null) {
            compType = obj.getClass();
        }
        int newArrLength = (array != null ? array.length + 1 : 1);
        @SuppressWarnings("unchecked")
        A[] newArr = (A[]) Array.newInstance(compType, newArrLength);
        if (array != null) {
            System.arraycopy(array, 0, newArr, 0, array.length);
        }
        newArr[newArr.length - 1] = obj;
        return newArr;
    }

    /**
     * 比较两个对象的内容(equals),如果两个对象相等则返回{@code true},如果两个中有一个为{@code null}则返回{@code false}.
     * 如果两个对象都是{@code null}则返回{@code true}.如果传入的参数类型是数组,则比较的数组里的对象内容,而不是数组引用比较.
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeEquals("hello","hello"); //---> true
     * ObjectUtils.nullSafeEquals("hello","hell"); //--->false;
     * ObjectUtils.nullSafeEquals(4,4); //---> true
     * ObjectUtils.nullSafeEquals(new String[]{"aaaa","bbb"},new String[]{"aaaa","bbb"}); //---> true
     * </pre>
     *
     * @param o1 第一个比较对象
     * @param o2 第二个比较对象
     * @return 判断两个对象内容是否相等
     * @see java.util.Arrays#equals(Object[], Object[])
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            if (o1 instanceof Object[] && o2 instanceof Object[]) {
                return Arrays.equals((Object[]) o1, (Object[]) o2);
            }
            if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[]) o2);
            }
            if (o1 instanceof byte[] && o2 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[]) o2);
            }
            if (o1 instanceof char[] && o2 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[]) o2);
            }
            if (o1 instanceof double[] && o2 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[]) o2);
            }
            if (o1 instanceof float[] && o2 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[]) o2);
            }
            if (o1 instanceof int[] && o2 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[]) o2);
            }
            if (o1 instanceof long[] && o2 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[]) o2);
            }
            if (o1 instanceof short[] && o2 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[]) o2);
            }
        }
        return false;
    }

    /**
     * 返回对象的hashCode值，经常返回的hashCode值是{@code Object.hashCode()},如果对象为{@code null}则返回0.
     * 如果对象是一个数组，则会委托给其中匹配类型的{@code nullSafeHashCode}
     *
     * @param obj 对象
     * @return 返回对象的hashCode值
     * @see #nullSafeHashCode(Object[])
     * @see #nullSafeHashCode(boolean[])
     * @see #nullSafeHashCode(byte[])
     * @see #nullSafeHashCode(char[])
     * @see #nullSafeHashCode(double[])
     * @see #nullSafeHashCode(float[])
     * @see #nullSafeHashCode(int[])
     * @see #nullSafeHashCode(long[])
     * @see #nullSafeHashCode(short[])
     */
    public static int nullSafeHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                return nullSafeHashCode((Object[]) obj);
            }
            if (obj instanceof boolean[]) {
                return nullSafeHashCode((boolean[]) obj);
            }
            if (obj instanceof byte[]) {
                return nullSafeHashCode((byte[]) obj);
            }
            if (obj instanceof char[]) {
                return nullSafeHashCode((char[]) obj);
            }
            if (obj instanceof double[]) {
                return nullSafeHashCode((double[]) obj);
            }
            if (obj instanceof float[]) {
                return nullSafeHashCode((float[]) obj);
            }
            if (obj instanceof int[]) {
                return nullSafeHashCode((int[]) obj);
            }
            if (obj instanceof long[]) {
                return nullSafeHashCode((long[]) obj);
            }
            if (obj instanceof short[]) {
                return nullSafeHashCode((short[]) obj);
            }
        }
        return obj.hashCode();
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new Object[]{"hello","ok"});
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(Object[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (Object element : array) {
            hash = MULTIPLIER * hash + nullSafeHashCode(element);
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new boolean[]{true,true,false}); //---> 1430926
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (boolean element : array) {
            hash = MULTIPLIER * hash + hashCode(element);
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new byte[]{8,16}); //---> 6991
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(byte[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (byte element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new char[]{'a','b'}); //---> 9832
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(char[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (char element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new double[]{2.2,2.1}); //---> -432792923
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(double[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (double element : array) {
            hash = MULTIPLIER * hash + hashCode(element);
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new float[]{2.2f,2.1f}); //---> 26430848
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(float[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (float element : array) {
            hash = MULTIPLIER * hash + hashCode(element);
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new int[]{2,2}); //---> 6791
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(int[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (int element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new long[]{2L,2L}); //---> 6791
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(long[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (long element : array) {
            hash = MULTIPLIER * hash + hashCode(element);
        }
        return hash;
    }

    /**
     * 计算数组的hashCode值
     * <p/>
     * <pre class="code">
     * ObjectUtils.nullSafeHashCode(new short[]{2,2}); //---> 6791
     * </pre>
     *
     * @param array 数组
     * @return 返回一个基于数组内容的hashCode值, 如果数组为{@code null}则返回0
     */
    public static int nullSafeHashCode(short[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        for (short element : array) {
            hash = MULTIPLIER * hash + element;
        }
        return hash;
    }

    /**
     * 该hashCode值返回的就是{@linkplain Boolean#hashCode()}的值
     *
     * @see Boolean#hashCode()
     */
    public static int hashCode(boolean bool) {
        return (bool ? 1231 : 1237);
    }

    /**
     * 该hashCode值返回的就是{@linkplain Double#hashCode()}的值
     *
     * @see Double#hashCode()
     */
    public static int hashCode(double dbl) {
        return hashCode(Double.doubleToLongBits(dbl));
    }

    /**
     * 该hashCode值返回的就是{@linkplain Float#hashCode()}的值
     *
     * @see Float#hashCode()
     */
    public static int hashCode(float flt) {
        return Float.floatToIntBits(flt);
    }

    /**
     * 该hashCode值返回的就是{@linkplain Long#hashCode()}的值
     *
     * @see Long#hashCode()
     */
    public static int hashCode(long lng) {
        return (int) (lng ^ (lng >>> 32));
    }
}
