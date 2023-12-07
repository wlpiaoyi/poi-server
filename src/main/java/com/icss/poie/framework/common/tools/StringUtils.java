package com.icss.poie.framework.common.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class StringUtils {

    static {

    }

    /**
     * 已废弃请使用com.filling.framework.common.tools.data.DataUtils.base64Encode
     * @param bytes
     * @return
     */
    @Deprecated
    public static String base64Encode(byte[] bytes) {
        return Base64.getMimeEncoder().encodeToString(bytes);
    }
    @Deprecated
    public static String base64Encode(String encodeStr, String charseName){
        try {
            return Base64.getMimeEncoder().encodeToString(encodeStr.getBytes(charseName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Deprecated
    public static String base64Encode(String encodeStr){
        return StringUtils.base64Encode(encodeStr, "UTF-8");
    }

    @Deprecated
    public static String base64Decode(String decodeStr, String charseName){
        try {
            byte[] bytes = StringUtils.base64DecodeToBytes(decodeStr);
            return new String(bytes, charseName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Deprecated
    public static byte[] base64DecodeToBytes(String decodeStr){
        return Base64.getMimeDecoder().decode(decodeStr);
    }
    @Deprecated
    public static String base64Decode(String decodeStr){
        return StringUtils.base64Decode(decodeStr, "UTF-8");
    }

    /**
     * 已废弃请使用com.filling.framework.common.tools.ValueUtils.isBlank
     * @param arg
     * @return
     */
    @Deprecated
    public static boolean isBlank(String arg){
        return arg == null || arg.length() == 0;
    }
    @Deprecated
    public static String toJson(Object obj){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }

    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID32S(int number){
        if(number < 1){
            return null;
        }
        StringBuilder uuid = new StringBuilder();
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = StringUtils.getUUID32();
        }
        return retArray;
    }

    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID64S(int number){
        if(number < 1){
            return null;
        }
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = StringUtils.getUUID64();
        }
        return retArray;
    }


    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID64(){
        String uuid32_1 = StringUtils.getUUID32();
        String uuid32_2 = StringUtils.getUUID32();
        return uuid32_1 + uuid32_2;
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    /**
     * 下划线名称转换成驼峰
     * @param name
     * @return
     */
    public static String parseUnderlineToHump(String name){
        String[] args = name.split("\\_");
        StringBuffer sb = new StringBuffer();
        for (String arg : args){
            if(ValueUtils.isBlank(arg)){ continue; }
            if(arg.length() == 1){ sb.append(arg.toUpperCase()); continue; }
            sb.append(arg.substring(0, 1).toUpperCase());
            if(arg.length() == 1){ continue; }
            sb.append(arg.substring(1).toLowerCase());
        }
        if(sb.length() == 0){ return name; }
        return sb.toString();
    }
    /**
     * 驼峰名称转换成下划线 upperNum=2
     * @param name
     * @return
     */
    public static String parseHumpToUnderline(String name){
        return parseHumpToUnderline(name, 2);
    }

    /**
     * 驼峰名称转换成下划线
     * @param name
     * @param upperNum 连续大写前缀数量
     * @return
     */
    public static String parseHumpToUnderline(String name, int upperNum){
        if(upperNum < 1){
            upperNum = 1;
        }
        char[] cs = name.toCharArray();
        StringBuffer res = new StringBuffer();
        int up_i = 0;
        int c_i = 0;
        for(char c : cs){
            c_i ++;
            int ci = c;
            if (ci < 65 || ci > 90){
                res.append(c);
                if(up_i > upperNum){
                    res.insert(res.length() - 1, '_');
                }
                up_i = 0;
                continue;
            }
            up_i ++;
            res.append(((char)(ci + 32)));
            if(c_i == 1){
                continue;
            }
            if(up_i > 1){
                continue;
            }
            res.insert(res.length() - 1, '_');
        }
        return res.toString();
    }

//    public static void main(String[] args) {
//        String a = "ab6c_d3eg_f3c";
//        String b = parseUnderlineToHump(a);
//        String c = parseHumpToUnderline(b);
//        String d = "AbcDEGFhEFf";
//        String e = parseHumpToUnderline(d, 3);
//        System.out.println(a + "     " + b + "     " + c + "     " + d + "     " + e);
//    }

}
