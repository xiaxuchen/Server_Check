package com.cxyz.check.util.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

    /**
     * 获取首拼
     * @param name 字符串
     * @return
     */
    public static String getFirstSpelling(String name)
    {
        char[] charArr = name.toCharArray();
        StringBuilder builder = new StringBuilder();
        for(char c:charArr)
        {
            builder.append(getSpelling(c).charAt(0));
        }
        return builder.toString();
    }

    public static String getSpelling(char c)
    {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            final String[] strings = PinyinHelper.toHanyuPinyinStringArray(c, format);
            if(strings == null)
            {
                return String.valueOf(c);
            }
            return strings[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            return String.valueOf(c);
        }
    }
}
