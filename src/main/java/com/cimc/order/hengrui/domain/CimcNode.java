package com.cimc.order.hengrui.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: dingyongxin
 * @email 1358547120@qq.com
 * @create: 2021-11-11 10:57
 **/
@Data
public class CimcNode implements Serializable {
    private static final long serialVersionUID = 1L;
    private String BillNumber;         //中集运单号
    private String OrderId;         //中集订单号
    private String XMNO;         //客户出库单号
    private String Time;         //节点时间
    private String City;         //节点城市
    private String CarNo;         //车牌号
    private String CarName;         //司机
    private String GetName;         //收件人
    private String Jian;         //件数
    private String Weight;         //重量
    private String Volume;         //体积
    private List<Map<String, String>> Picture;         //图片
    private String CarTel;         //司机电话
    private String Company;         //节点站点
    private String UserName;         //节点操作人
    private String Depart;         //节点省份
    private String WDQJ;         //温度区间
    private String SheBeiHao;         //设备号
    private String AccountNumber;         //客户账号
    private String BillCode;         //节点码
    private String BillNote;         //节点描述
    private String District;        //区
    private String Address;         //详细地址
    private String[] xmnos;         //客户出库单号


    /**
     * 客户数据代码
     */
    private String customerCode;

    /**
     * 客户数据名称
     */
    private String customerValue;
}
