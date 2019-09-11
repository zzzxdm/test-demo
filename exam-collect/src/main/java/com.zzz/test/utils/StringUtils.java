package com.zzz.test.utils;

public class StringUtils {

    /**
     * 字符串替换
     *
     * @param source
     * @param target
     * @param replaceStr
     * @return
     */
    public static String replace(String source, String target, String replaceStr) {
        if (source == null || target == null || replaceStr == null) {
            return source;
        }
        int index = indexOf(source, target);
        if (index == -1 || target.equals(replaceStr)) {
            return source;
        }
        char[] sourceArr = source.toCharArray();
        char[] replaceArr = replaceStr.toCharArray();
        int finalLength = source.length() + replaceStr.length() - target.length();
        char[] resultArr = new char[finalLength];
        for (int i = 0; i < index; i++) {
            resultArr[i] = sourceArr[i];
        }
        int tempIdx = 0;
        int idxEnd = index + replaceStr.length();
        for (int i = index; i < idxEnd; i++) {
            resultArr[i] = replaceArr[tempIdx++];
        }
        tempIdx = index + target.length();
        for (int i = idxEnd; i < finalLength; i++) {
            resultArr[i] = sourceArr[tempIdx++];
        }
        return String.valueOf(resultArr);
    }

    /**
     * 计算字符串的索引
     *
     * @param source
     * @param target
     * @return
     */
    public static int indexOf(String source, String target) {
        if ((source == null || target == null)
                || source.length() < target.length()) {
            return -1;
        }
        char[] sourceArr = source.toCharArray();
        char[] targetArr = target.toCharArray();
        for (int i = 0; i < sourceArr.length; i++) {
            boolean equalsFlag = true;
            int idx = i;
            for (int j = 0; j < targetArr.length; j++) {
                if (sourceArr[i] != targetArr[j]) {
                    equalsFlag = false;
                    break;
                } else {
                    i++;
                    continue;
                }
            }
            if (equalsFlag) {
                return idx;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String source = "abcdef";
        String target = "cd";
        System.out.println(source.indexOf(target));
        System.out.println(indexOf(source, target));
        source = replace(source, target, "cd");
        System.out.println(source);
        System.out.println(indexOf(null, ""));
    }
}
