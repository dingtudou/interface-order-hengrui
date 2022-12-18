package com.cimc.order.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: crm_interface_guoyao
 * @description
 * @author: dingyongxin
 * @email 1358547120@qq.com
 * @create: 2021-08-04 14:00
 **/
public class PhoneNumUtils {
    private static Pattern phonePattern=Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");

    /**
     * @function 获取该字符串中的手机号
     * @author dingyongxin
     * @annotation 该方法返回多个手机号
     * @param str
     * @return String 返回手机号
     */
    public static String getPhoneNum(String str) {
        String phonenum="";
        if (StringUtils.isNotEmpty(str)) {
            Matcher m=phonePattern.matcher(str);
            if(m!=null){
                while(m.find()){
                    phonenum=phonenum+m.group()+",";
                }
            }
        }
        if (StringUtils.isNotEmpty(phonenum)) {
            phonenum=phonenum.substring(0,phonenum.length()-1);
        }
        return  phonenum;
    }

/*    public static void main(String[] args) {
        String str="河北省石家庄市长安区栗中路河北省石家庄市长安区栗中路1号礼域尚城8-114号*150281859511号礼域尚城8-114号*15028185951";
        String phone=getPhoneNum(str);
        System.out.println(phone);
    }*/
}
