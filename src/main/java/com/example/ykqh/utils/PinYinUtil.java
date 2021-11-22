package com.example.ykqh.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author 杨昆
 * @date 2021/6/17 10:35
 * @describe 拼音工具类
 */
public class PinYinUtil {
    private static final Integer MAX_VALUE = 12;

    /**
     * 汉字取首字母小写
     */
    public static String toFirstChar(String str){
        StringBuilder context = new StringBuilder();
        for (int j = 0; j <  str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                context.append(pinyinArray[0].charAt(0));
            } else {
                context.append(word);
            }
        }
        // 限定最大取12位
        if (context.length() > MAX_VALUE) {
            return context.substring(0, MAX_VALUE);
        }
        return context.toString();
    }

    /**
     * 汉字取首字母大写
     */
    public static String toBigFirstChar(String chines) {
        StringBuilder pinyinName = new StringBuilder();
        chines = chines.replaceAll("[^a-zA-Z_\\u4e00-\\u9fa5]", "");
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (c > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(c);
            }
        }
        // 限定最大取12位
        if (pinyinName.length() > MAX_VALUE) {
            return pinyinName.substring(0, MAX_VALUE);
        }
        return pinyinName.toString();
    }
}