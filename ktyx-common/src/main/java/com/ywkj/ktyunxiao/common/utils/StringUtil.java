package com.ywkj.ktyunxiao.common.utils;

import com.ywkj.ktyunxiao.common.exception.ParamException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiWenHao
 * @date 2018/5/22 17:02
 */
public class StringUtil {


    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !(str == null || "".equals(StringUtil.replaceSpace(str)));
    }

    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    /**
     * 判断是否是非法字符
     * @param str
     * @return
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


    /**
     * 对数字固定长度，不足在前面补零
     * @param integer
     * @param length
     * @return
     */
    public static String getIdCard(Integer integer,int length) {
        String s = integer.toString();
        if (s.length() > length) {
            s = s.substring(s.length() - length, s.length());
        }else {
            s = String.format("%0" + length + "d", integer);
        }
        return s;
    }

    /**
     * 获取uuid
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 删除空格
     * @param s
     * @return
     */
    public static String replaceSpace(String s){
        return s.replaceAll("[\\s]+","");
    }

    /**
     * 去除开头和结尾
     * @return
     */
    public static String cutStartAndEnd(String s){
        String s1 = replaceSpace(s);
        return s1.substring(1, s1.length() - 1);
    }

    /**
     * URL解码
     * @param s
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlDecoder(String s) throws UnsupportedEncodingException {
        return URLDecoder.decode(s,"UTF-8");
    }

}
