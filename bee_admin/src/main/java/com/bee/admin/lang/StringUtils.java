/*
*/
package com.bee.admin.lang;


import com.bee.admin.utils.CollectionUtils;

import java.util.*;

/**
 * StringUtils类主要提供了针对字符串，字符串数组常用的工具类方法
 * Created by jiankangjin on 2014/4/24.
 */
public abstract class StringUtils {

    //---------------------------------------------------------------------
    // constant fields
    // ---------------------------------------------------------------------

    /**
     * LINUX系统下目录分隔符
     */
    public static final String LINUX_FOLDER_SEPARATOR = "/";

    /**
     * Windows下的目录分隔符
     */
    public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    /**
     * 文件名和文件类型的分隔符
     */
    public static final String EXTENSION_SEPARATOR = ".";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    private static final int PAD_LIMIT = 8192;


    //---------------------------------------------------------------------
    // 比较常用的处理字符串String类的操作方法
    // ---------------------------------------------------------------------


    /**
     * 判断对象是否为空
     * <p>
     * 该方法用于判断参数是否{@code null}以及是否是一个空(""),所以当一个非{@code String}类&非{@code null}作为参数传入，其返回值总是为{@code true}.
     * </p>
     * <pre class="code">
     * StringUtils.isEmpty("");// ---> true
     * //----------------------------------
     * String str1 = "2";
     * StringUtils.isEmpty(str1);// ---> false
     * //----------------------------------
     * StringUtils.isEmpty(2);// ---> false
     * </pre>
     *
     * @param str 字符串
     * @return 当字符串是{@code null}或""都返回{@code true}，否则都返回{@code false}
     */
    @Deprecated
    public static boolean isEmpty(Object str) {
        return (str == null) || ("".equals(str));
    }

    /**
     * 判断字符串是否为{@code null}或空字符串。判断字符串是否为空请使用该方法，请不要使用其重载方法{@linkplain #isEmpty(Object)}
     * @param str 字符串
     * @return 如果字符串是{@code}或空字符串则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(CharSequence str) {
        return !hasLength(str);
    }

    /**
     * 判断字符串是否为{@code null}或空字符串。判断字符串是否为空请使用该方法，请不要使用其重载方法{@linkplain #isEmpty(Object)}
     * @param str 字符串
     * @return 如果字符串是{@code}或空字符串则返回{@code true}，否则返回{@code false}
     */
    public static boolean isEmpty(String str) {
        return isEmpty((CharSequence)str);
    }

    /**
     * 判断字符串是否不是空
     * <p/>
     * <p>
     * 该方法用于判断参数是否为{@code null}和一个空字符"",当且仅当传入参数既不是{@code null}也不是空字符""是返回{@code true}.
     * </p>
     * <p/>
     * <pre class="code">
     * StringUtils.isNotEmpty("");// ---> flase
     * StringUtils.isNotEmpty(2);// ---> true
     * StringUtils.isNotEmpty(" ");// ---> true
     * </pre>
     *
     * @param str 字符串
     * @return 当且仅当字符串既不为{@code null}也不是空字符串""时则返回{@code true}，否则返回{@code false}
     * @see #isEmpty(Object)
     */
    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为既不为{@code null}，字符串长度也不为0.当传入参数是一个空白符时也返回{@code true}
     * <p/>
     * <pre class="code">
     * StringUtils.hasLength(null);// ---> false
     * StringUtils.hasLength("");// ---> false
     * StringUtils.hasLength(" ");// ---> true
     * StringUtils.hasLength("Hi");// ---> true
     * </pre>
     *
     * @param str 字符串
     * @return 当且仅当字符串类型不为{@code null},长度也不为0时就返回{@code true},反之则返回{@code false}.
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null) && (str.length() > 0);
    }

    /**
     * 判断字符串是否为既不为{@code null}，字符串长度也不为0.当传入参数是一个空白符时也返回{@code true}
     *
     * @param str 字符串
     * @return 当且仅当字符串类型不为{@code null},长度也不为0时就返回{@code true},反之则返回{@code false}.
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * 判断字符串是否存在文本字符，即字符只是存在一个非空白字符。
     * <p/>
     * <pre>
     *  StringUtils.hasText(null); //--- > false
     *  StringUtils.hasText(""); //---> false
     *  StringUtils.hasText(" "); //---> false
     *  StringUtils.hasText(" abc"); //---> true
     *  StringUtils.hasText("a"); //---> true
     * </pre>
     *
     * @param str 字符串
     * @return 当字符串至少存在一个不为空白字符时返回{@code true},否则返回{@code false}
     * @see Character#isWhitespace(char)
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        for (int len = str.length(), i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否存在文本字符，即字符只是存在一个非空白字符。
     *
     * @param str 字符串
     * @return 当字符串至少存在一个不为空白字符时返回{@code true},否则返回{@code false}
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    /**
     * 判断字符串中是否存在空白符,方法会检查字符串是否为{@code null}
     * <p/>
     * <pre class="code">
     * StringUtils.containsWhitespace(null); //---> false
     * StringUtils.containsWhitespace(""); //---> false
     * StringUtils.containsWhitespace(" "); //---> true
     * StringUtils.containsWhitespace("ax x"); //---> true
     * </pre>
     *
     * @param str 字符串
     * @return 当字符串存在一个空白符时就返回{@code true},否则返回{@code false}
     * @see Character#isWhitespace(char)
     */
    public static boolean containsWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        for (int len = str.length(), i = 0; i < len; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串中是否存在空白符,方法会检查字符串是否为{@code null}
     *
     * @param str 字符串
     * @return 当字符串存在一个空白符时就返回{@code true},否则返回{@code false}
     * @see #containsWhitespace(CharSequence)
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence) str);
    }

    /**
     * 去除字符串左右两侧的空白符
     * <p/>
     * <pre>
     *  StringUtils.trim(null); //---> null
     *  StringUtils.trim(""); //---> ""
     *  StringUtils.trim("    "); //---> ""
     *  StringUtils.trim("  a b  "); //---> a b
     * </pre>
     *
     * @param str 字符串
     * @return 返回已经去除左右两边的空白的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trim(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        //删除左边的空白符
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 去除字符串中所有的空白字符
     * <p/>
     * <pre class="code">
     * StringUtils.trimAllWhiteSpace(null); //---> null;
     * StringUtils.trimAllWhiteSpace(" a "); //---> a
     * StringUtils.trimAllWhiteSpace(" a b c "); //---> abc
     * </pre>
     *
     * @param str 字符串
     * @return 返回去除后的字符串
     * @see Character#isWhitespace(char)
     */
    public static String trimAllWhiteSpace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * 去除字符串左侧的空白字符
     * <p/>
     * <pre class="code">
     * StringUtils.trimLeftWhiteSpace(null); //---> null
     * StringUtils.trimLeftWhiteSpace("ab"); //---> null
     * StringUtils.trimLeftWhiteSpace(("  ab c");//---> ab c
     * </pre>
     *
     * @param str 字符串
     * @return 返回去除后的字符串
     */
    public static String trimLeftWhiteSpace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * 去除字符串右侧的空白字符
     * <p/>
     * <pre class="code">
     * StringUtils.trimRightWhiteSpace(null); //---> null
     * StringUtils.trimRightWhiteSpace("ab"); //---> null
     * StringUtils.trimRightWhiteSpace((" ab c   ");//--->  ab c
     * </pre>
     *
     * @param str 字符串
     * @return 返回去除后的字符串
     */
    public static String trimRightWhiteSpace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 判断字符串中是否以给定的字符串开头，不考虑大小写问题
     * <p/>
     * <pre class="code">
     * StringUtils.startsWithIgnoreCase("abcd",null); //---> false
     * StringUtils.startsWithIgnoreCase("abcd","ab"); //---> true
     * StringUtils.startsWithIgnoreCase("abcd","bc"); //---> false
     * StringUtils.startsWithIgnoreCase("abcd","AB"); //---> true
     * StringUtils.startsWithIgnoreCase(null,"ab"); //---> false
     * </pre>
     *
     * @param str    字符串
     * @param prefix 给定的字符串，以该字符串开头
     * @return 如果字符串{code str}是以{@code prefix}开头(忽略大小写)则返回{@code true},否则返回{@code false}
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if ((null == str) || (null == prefix)) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lowerCaseStr = str.substring(0, prefix.length()).toLowerCase();
        String lowerCasePrefix = prefix.toLowerCase();
        return lowerCasePrefix.equals(lowerCaseStr);
    }

    /**
     * 判断字符串中是否以给定的字符串结尾,不考虑大小写问题.
     * <p/>
     * <pre class="code">
     * StringUtils.endsWithIgnoreCase("ddcbab",null); //---> false
     * StringUtils.endsWithIgnoreCase("ddcbab","ab"); //---> true
     * StringUtils.endsWithIgnoreCase("ddcbab","bc"); //---> false
     * StringUtils.endsWithIgnoreCase("ddcbab","AB"); //---> true
     * StringUtils.endsWithIgnoreCase(null,"ab"); //---> false
     * </pre>
     *
     * @param str    字符串
     * @param suffix 给定的字符串，以该字符串开头
     * @return 如果字符串{code str}是以{@code prefix}开头(忽略大小写)则返回{@code true},否则返回{@code false}
     * @see String#endsWith(String)
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if ((null == str) || (null == suffix)) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        if (str.length() < suffix.length()) {
            return false;
        }
        String lowerStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lowerSuffix = suffix.toLowerCase();
        return lowerSuffix.equals(lowerStr);
    }

    /*public static boolean startsWithIgnoreCase(String str, String prefix, int offset) {
        if ((null == str) || (null == prefix)) {
            return false;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        if ((offset < 0) || (offset > str.length() - prefix.length())) {
            return false;
        }
        if (str.startsWith(prefix, offset)) {
            return true;
        }
        String lowerCaseStr = str.substring(offset, offset + prefix.length()).toLowerCase();
        String lowerCasePrefix = prefix.toLowerCase();
        return lowerCaseStr.equals(lowerCasePrefix);
    }*/


    /**
     * 判断字符串从指定位置是否和给定的字符串匹配
     * <p/>
     * <pre class="code">
     * StringUtils.matchSubstring("abcdef",2,"cd"); //---> true
     * StringUtils.matchSubstring("abc",2,"abcd"); //---> false
     * </pre>
     *
     * @param str       字符串
     * @param index     匹配的起始位置
     * @param substring 匹配的字符串
     * @return 匹配成功则返回{@code true},否则返回{@code false}
     */
    public static boolean matchSubstring(CharSequence str, int index, CharSequence substring) {
        if (str == null || null == substring) {
            return false;
        }

        if (str.length() < substring.length()) {
            return false;
        }

        if (index < 0 || index > (str.length() - substring.length())) {
            return false;
        }

        for (int strLen = str.length(), subLen = substring.length(), j = 0; j < subLen; j++) {
            int i = j + index;
            if (str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 替换字符串中所有出现了{@code oldPattern}为{@code newPattern}.
     * <p/>
     * <pre class="code">
     * StringUtils.replace("hello world","l","x"); //---> "hexxo worxd"
     * </pre>
     *
     * @param str        字符串
     * @param oldPattern 需要替换的字符串
     * @param newPattern 新的字符串
     * @return 返回替换后的字符串
     */
    public static String replace(String str, String oldPattern, String newPattern) {
        if ((!hasLength(str)) || (!hasLength(oldPattern)) || (null == newPattern)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        int pos = 0;
        int index = str.indexOf(oldPattern, pos);
        int patternLen = oldPattern.length();

        while (index >= 0) {
            sb.append(str.substring(pos, index));
            sb.append(newPattern);
            pos = index + patternLen;
            index = str.indexOf(oldPattern, pos);
        }
        sb.append(str.substring(pos));
        return sb.toString();
    }

    /**
     * 将字符串{@code str}中的出现了指定子串{@code pattern}全部删除,删除的字符串不支持正则表达式.
     * <p/>
     * <pre class="code">
     * StringUtils.delete("hello world","l"); //---> "heo word";
     * </pre>
     *
     * @param str     字符串
     * @param pattern 要删除的字符串
     * @return 返回删除后的字符串
     */
    public static String delete(String str, String pattern) {
        return replace(str, pattern, "");
    }

    /**
     * 将字符串的第一个字符(必须在{@linkplain Character#toUpperCase(char)}中，否则就不会改变)转换为大写.
     * <p/>
     * <pre class="code">
     * StringUtils.capitalize("she si just a kid"); //---> "She is just a kid"
     * </pre>
     *
     * @param str 字符串
     * @return 返回字符串中的第一个字符转换为大写
     */
    public static String capitalize(String str) {
        return changeCharacterCase(str, 0, true);
    }

    /**
     * 将字符串的第一个字符(必须在{@linkplain Character#toLowerCase(char)}中，否则就不会改变)转换为小写.
     * <p/>
     * <pre class="code">
     * StringUtils.uncapitalize("Hello"); //---> "hello"
     * </pre>
     *
     * @param str 字符串
     * @return 返回字符串中的第一个字符转换为小写
     */
    public static String uncapitalize(String str) {
        return changeCharacterCase(str, 0, false);
    }

    /**
     * 将字符串中指定位置({@code index})的字符(必须在{@linkplain Character#toLowerCase(char)})转为大(小)写,否则就不会改变
     * <pre class="code">
     * StringUtils.changeCharacterCase("Hello",2,true); //---> "HeLlo"
     * </pre>
     *
     * @param str     字符串
     * @param index   大小写更改位置
     * @param capital 大写还是小写，但值为{@code true}时则大写，值为{@code false}时则小写
     * @return 返回修改后的字符串
     */
    public static String changeCharacterCase(String str, int index, boolean capital) {
        if (!hasLength(str)) {
            return str;
        }
        int pos = 0;
        if (index < 0)
            pos = str.length() + index;
        else {
            pos = index;
        }
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(str.subSequence(0, pos));
        if (capital) {
            sb.append(Character.toUpperCase(str.charAt(pos)));
        } else {
            sb.append(Character.toLowerCase(str.charAt(pos)));
        }
        sb.append(str.substring(pos + 1));
        return sb.toString();
    }

    /**
     * 从文件路径中提取文件名,不支持Windows系统下的路径
     * <pre class="code">
     * StringUtils.getFilename("/opt/app/config.proerties"); //---> config.proerties
     * </pre>
     *
     * @param path 文件路径
     * @return 返回文件名或者返回{@code null}如果为空时
     */
    public static String getFilename(String path) {
        if (!hasLength(path)) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(WINDOWS_FOLDER_SEPARATOR);
        if (separatorIndex == -1) {
            separatorIndex = path.lastIndexOf(LINUX_FOLDER_SEPARATOR);
        }
        return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
    }

    /**
     * 从文件路径中提取文件后缀名
     * <pre class="code">
     * StringUtils.getFilenameExtension("/opt/app/config.proerties"); //---> properties
     * </pre>
     *
     * @param path 文件路径
     * @return 返回文件后缀名或者返回{@code null}如果为空时
     */
    public static String getFilenameExtension(String path) {
        String filename = getFilename(path);
        int extIndex = filename.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        return filename.substring(extIndex + 1);
    }

    /**
     * 从文件路径中删除文件后缀名
     * <pre class="code">
     * StringUtils.stripFilenameExtension("/opt/app/config.proerties"); //---> /opt/app/config
     * </pre>
     *
     * @param path 文件路径
     * @return 返回不带文件后缀名的文件路径
     */
    public static String stripFilenameExtension(String path) {
        String filename = getFilename(path);
        int extIndex = filename.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return path;
        }

        return filename.substring(0, extIndex);
    }

    /**
     * 将相对路径{@code relativePath}转为相对于{@code path}路径下的文件路径
     *
     * <pre class="code">
     * StringUtils.applyRelativePath("/opt/app/config.proerties", "/xml/jdbc.xml"); //---> /opt/app/xml/jdbc.properties
     * StringUtils.applyRelativePath("/opt/app/config/", "/xml/jdbc.xml"); //---> /opt/app/config/xml/jdbc.properties
     * </pre>
     *
     * @param path         路径(一般是文件的绝对路径)
     * @param relativePath 相对路径
     * @return 返回相对路径的全路径
     */
    public static String applyRelativePath(String path, String relativePath) {
        if (!hasText(path) || !hasText(relativePath)) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(LINUX_FOLDER_SEPARATOR);
        if (separatorIndex != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(path.substring(0, separatorIndex));
            if (!relativePath.startsWith(LINUX_FOLDER_SEPARATOR)) {
                sb.append(LINUX_FOLDER_SEPARATOR);
            }
            return sb.append(relativePath).toString();
        } else {
            return relativePath;
        }
    }


    //---------------------------------------------------------------------
    // 比较常用的处理字符串String类数组的操作方法
    // ---------------------------------------------------------------------


    /**
     * 向数组中追加一个字符串，返回一个新的字符串数组.
     * <p/>
     * <pre class="code">
     * StringUtils.addStringToArray(new String[]{"hello"},"world"); //---> [hello,world]
     * StringUtils.addStringToArray(null,"hah"); //---> [haha]
     * </pre>
     *
     * @param array  原始数组
     * @param addStr 追加的字符串
     * @return 返回一个新的数组，该数组永远不为{@code null}
     */
    public static String[] addStringToArray(String[] array, String addStr) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[]{addStr};
        }
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = addStr;
        return newArray;
    }

    /**
     * 将两个字符串数组对接,返回一个新的字符串数组.
     * <p/>
     * <pre class="code">
     * StringUtils.concatStringArrays(new String[]{"girl","women"},new String[]{"boy","girl"}); //---> [girl,women,boy,girl]
     * </pre>
     *
     * @param arr1 数组
     * @param arr2 数组
     * @return 返回一个新的字符串数组
     */
    public static String[] concatStringArrays(String[] arr1, String[] arr2) {
        if (ObjectUtils.isEmpty(arr1)) {
            return arr2;
        }
        if (ObjectUtils.isEmpty(arr2)) {
            return arr1;
        }
        String[] newArr = new String[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, newArr, 0, arr1.length);
        System.arraycopy(arr2, 0, newArr, arr1.length, arr2.length);
        return newArr;
    }

    /**
     * 合并两个数组,其中数组元素重复的直计算一次.
     * <p/>
     * <pre class="code">
     * StringUtils.mergeStringArrays(new String[]{"girl","women"},new String[]{"boy","girl"}); //---> [girl,women,boy]
     * </pre>
     *
     * @param array1 数组
     * @param array2 数组
     * @return 返回合并后的新数组
     */
    public static String[] mergeStringArrays(String[] array1, String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        }
        if (ObjectUtils.isEmpty(array2)) {
            return array1;
        }
        List<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(array1));
        for (String str : array2) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        return toStringArray(result);
    }

    /**
     * 数组排序
     * <p/>
     * <pre class="code">
     * StringUtils.sortStringArray(new String[]{"hello","boy","amazing"}); //---> [amazing,boy,hello]
     * </pre>
     *
     * @param array 数组
     */
    public static void sortStringArray(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return;
        }
        Arrays.sort(array);
    }

    /**
     * 集合对象转为数组
     * <p/>
     * <pre class="code">
     * StringUtils.toStringArray(Arrays.asList("hello","boy","amazing")); //---> [boy,hello,amazing]
     * </pre>
     *
     * @param collection 集合对象
     * @return 返回数组对象
     */
    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }
        return collection.toArray(new String[collection.size()]);
    }

    /**
     * 将枚举类中的所有枚举值转为数组
     * <p/>
     * <pre class="code">
     * StringUtils.toStringArray(genderEnum); //---> [male,female]
     * </pre>
     *
     * @param enumeration 枚举对象
     * @return 字符串数组
     */
    public static String[] toStringArray(Enumeration<String> enumeration) {
        if (enumeration == null) {
            return null;
        }
        List<String> list = Collections.list(enumeration);
        return list.toArray(new String[list.size()]);
    }

    public static String[] splitAtFirst(String toSplit, String delimter) {
        if (StringUtils.isEmpty(toSplit) || StringUtils.isEmpty(delimter)) {
            return null;
        }
        int offset = toSplit.indexOf(delimter);
        if (offset < 0) {
            return null;
        }
        return new String[]{toSplit.substring(0, offset), toSplit.substring(offset + delimter.length())};
    }

    public static String[] split(String toSplit, String delimter) {
        if (StringUtils.isEmpty(toSplit) || StringUtils.isEmpty(delimter)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(toSplit, delimter);
        List<String> values = new ArrayList<String>();
        String token;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            if (StringUtils.hasText(token)) {
                values.add(token);
            }
        }
        if (CollectionUtils.isNotEmpty(values)) {
            String[] splitOfStrs = new String[values.size()];
            values.toArray(splitOfStrs);
            return splitOfStrs;
        }
        return null;
    }

    /**
     * <p>Left pad a String with spaces (' ').</p>
     *
     * <p>The String is padded to the size of <code>size<code>.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *)   = null
     * StringUtils.leftPad("", 3)     = "   "
     * StringUtils.leftPad("bat", 3)  = "bat"
     * StringUtils.leftPad("bat", 5)  = "  bat"
     * StringUtils.leftPad("bat", 1)  = "bat"
     * StringUtils.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return left padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * <p>Left pad a String with a specified character.</p>
     *
     * <p>Pad to a size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return left padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     * @since 2.0
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    /**
     * @author mark
     * @param repeat
     * @param padChar
     * @return
     * @throws IndexOutOfBoundsException
     */
    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }


}
