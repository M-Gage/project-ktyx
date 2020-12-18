package com.ywkj.ktyunxiao.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author LiWenHao
 * @date 2017/12/25 23:12
 */
public class PinYinUtil {

    /**
     * 获取中文的首字母
     * @param chainStr  字符串
     * @return
     */
    public static String getPinYinHeadChar(String chainStr) {
        if(chainStr != null && !chainStr.trim().equalsIgnoreCase("")) {
            char[] strChar = chainStr.toCharArray();
            // 汉语拼音格式输出类
            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
            // 输出设置，大小写，音标方式等
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<strChar.length; i++) {
                char c = strChar[i];
                char pyc = strChar[i];
                //是中文或者a-z或者A-Z转换拼音
                if(String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        String[] pyStirngArray = PinyinHelper.toHanyuPinyinStringArray(strChar[i], hanYuPinOutputFormat);
                        if(null != pyStirngArray && pyStirngArray[0]!=null) {
                            pyc = pyStirngArray[0].charAt(0);
                            sb.append(pyc);
                        }
                    } catch(BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
        return null;
    }
}
