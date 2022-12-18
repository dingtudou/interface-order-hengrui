package com.cimc.order.hengrui.common;

import com.cimc.order.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: crm_interface_guoyao
 * @description
 * @author: dingyongxin
 * @email 1358547120@qq.com
 * @create: 2021-08-05 09:07
 **/
public class AddressUtils {
    /**
     * 解析地址
     * @author dingyongxin
     * @param address
     * @return
     */
    public static Map<String,String> addressResolution1(String address){
        Map<String,String> map=new HashMap<String,String>();
        String province="",city="";
        if ( StringUtils.isNotEmpty(address.trim())){
            //String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
            String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)";

            Matcher m= Pattern.compile(regex).matcher(address);

            while(m.find()){
                province=m.group("province");
                city=m.group("city");
            }

        }
        if (StringUtils.isEmpty(province)&&StringUtils.isNotEmpty(city)){
            province=city;
        }

        map.put("province",province.replace("市",""));
        map.put("city",city);
        return map;
    }

    /**
     * 解析地址
     */
    public static Map<String,String> addressResolution(String addressArea){
        Map<String,String> map=new HashMap<String,String>();
        String province = null,city = null;
        if (StringUtils.isNotBlank(addressArea.trim())){
            String[] addressAreas=addressArea.split(",");
            if (addressAreas.length==1){
                city=addressAreas[0];
            }else if(addressAreas.length==2){
                province=addressAreas[0];
                city=addressAreas[0];
            }else if(addressAreas.length>2){
                province=addressAreas[0];
                city=addressAreas[1];            }
        }
        map.put("province",province);
        map.put("city",city);
        return map;
    }
}
