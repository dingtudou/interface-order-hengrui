package com.cimc.order.common.utils;


import org.apache.ibatis.javassist.compiler.ast.StringL;

/**
 * @description
 * @author: dingyongxin
 * @create: 2021-07-20 15:07
 **/
public class StringUtils extends org.apache.commons.lang3.StringUtils
{
    /**
     * 提取字符串末尾提取数字
     */
    public static String getLastNumber(String str){
        String lastNumber = null;
        if (StringUtils.isNotBlank(str)) {
            String[] characterArray = str.split("");
            ArrayUtils.adverses(characterArray);
            boolean isNotNumeric=false;
            int digitsNum=0;
            for (int i = 0; i < characterArray.length; i++) {
                if (!StringUtils.isNumeric(characterArray[i])&&!isNotNumeric) {
                    digitsNum = i;
                    isNotNumeric=true;
                }
            }
            lastNumber=str.substring(str.length()-digitsNum);
        }
        return lastNumber;
    }
}
