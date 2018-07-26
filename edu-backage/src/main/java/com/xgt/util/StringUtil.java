package com.xgt.util;

import com.xgt.constant.SystemConstant;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.*;

import static com.xgt.constant.SystemConstant.COMMA;

/**
 * User: lb on 2016/9/7.
 * Date:2016-09-07-20:39
 * desc：
 */
public class StringUtil {

    public static String[] stringAnalytical(String string, String divisionChar) {
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);

        String[] str = new String[tokenizer.countTokens()];

        while (tokenizer.hasMoreTokens()) {
            str[i] = new String();
            str[i] = tokenizer.nextToken();
            i++;
        }

        return str;
    }

    /**
     * @Description 计算两个数值百分比
     * @author HeLiu
     * @date 2018/7/2 18:09
     */
    public static String getNum(Integer num1, Integer num2) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) num1 / (float) num2 * 100);
        return result + "%";
    }

    /**
     * gbk 字符串转 utf-8 字符串
     *
     * @author liuao
     * @date 2018/7/10 10:38
     */
    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    /**
     * gbk 字符串转 utf-8 字节
     *
     * @author liuao
     * @date 2018/7/10 10:37
     */
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }


    /**
     * @Description 拼接我的答案
     * @author HeLiu
     * @date 2018/7/16 21:14
     */
    public static String getMyAnswerStr(List<String> list) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String answer = list.get(i);
            if (StringUtils.isBlank(answer)) {
                str.append("空" + "|");
                continue;
            }
            str.append(list.get(i) + "|");
        }
        str.deleteCharAt(str.lastIndexOf("|"));
        return str.toString();
    }


    /**
     *  char 数组转 String list
     * @author liuao
     * @date 2018/7/13 14:59
     */
    public static List<String> charArr2StringList(char [] charArr){
        List<String> list = new LinkedList<>();
        if(null != charArr && charArr.length > 0){
            for (char ch : charArr) {
                list.add(String.valueOf(ch));
            }
        }
        return list;
    }

    /**
     *  string 数组转 String list
     * @author liuao
     * @date 2018/7/13 14:59
     */
    public static List<String> strArr2StringList(String [] strArr){
        List<String> list = new LinkedList<>();

        if(null != strArr && strArr.length > 0){

            Arrays.sort(strArr);
            for (String str : strArr) {
                list.add(str);
            }
        }
        return list;
    }


    /**
     *  将字符串里的每个字符排序，然后以逗号间隔 ，拼成新的字符串
     *
     * 例如 ：
     *
     *  b,a,c   - >    a,b,c
     *  bac  - > a,b,c
     *
     * @author liuao
     * @date 2018/7/17 19:21
     */
    public static String str2SortStr(String str){

        String tempStr = str.replaceAll(COMMA,StringUtils.EMPTY);
        tempStr = tempStr.replaceAll(StringUtils.SPACE ,StringUtils.EMPTY);
        char [] charArr = tempStr.toCharArray();
        Arrays.sort(charArr);

        StringBuilder sb = new StringBuilder();
        for (char ch: charArr) {
            sb.append(ch).append(COMMA);
        }
        sb = sb.deleteCharAt(sb.lastIndexOf(COMMA));
        return  sb.toString();
    }

}
