package com.cimc.order.hengrui.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName hengrui_order
 */
@TableName(value ="hengrui_order")
@Data
public class HengruiOrder implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 运单号
     */
    private String clientOrder;

    /**
     * 签名,($shippingorder _$synctime)md5后aes加密，测试环境密码：hrcdms@#%123456!
     */
    private String signature;

    /**
     * 接口类型（空/0.药品 1.样本）
     */
    private String type;

    /**
     * 时间（时间戳）
     */
    private String syncTime;

    /**
     * 下单人
     */
    private String inputOrderPerson;

    /**
     * 下单电话
     */
    private String inputOrderPhone;

    /**
     * 下单邮箱
     */
    private String inputOrderEmail;

    /**
     * 下单时间
     */
    private String inputOrderTime;

    /**
     * 付费账号
     */
    private String paidAccount;

    /**
     * 项目号
     */
    private String itemNumber;

    /**
     * 发件人
     */
    private String sender;

    /**
     * 投保金额
     */
    private String insuredAmount;

    /**
     * 发件电话
     */
    private String senderPhone;

    /**
     * 发件公司
     */
    private String sendCompany;

    /**
     * 发件区域
     */
    private String sendArea;

    /**
     * 发件科室
     */
    private String sendDepartment;

    /**
     * 发件地址
     */
    private String sendAddress;

    /**
     * 要求取件时间
     */
    private String requireSendTime;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 收件电话
     */
    private String receiverPhone;

    /**
     * 收件单位（公司）
     */
    private String receiverCompany;

    /**
     * 收件区域
     */
    private String receiverArea;

    /**
     * 收件科室
     */
    private String receiverDepartment;

    /**
     * 收件地址
     */
    private String receiverAddress;

    /**
     * 要求到货时间
     */
    private String requireGetTime;

    /**
     * 核酸要求说明
     */
    private String nucleinRequirement;

    /**
     * 是否使用温度计1、温度计2、温湿度计0.不使用
     */
    private String istemperature;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 中集返回状态码200成功400失败
     */
    private String cimcCode;

    /**
     * 中集返回消息
     */
    private String cimcMsg;

    /**
     * 中集返回转单主键取消时需要该参数
     */
    private String cimcOrderid;

    @TableField(exist = false)
    HengruiTestInfo testInfo;

    @TableField(exist = false)
    private List<HengruiDetail> detailList;


    /**
     * wms出库单下发接口响应标记,1为成功,0为失败
     */
    private String wmsReturnFlag;

    /**
     * wms出库单下发接口返回代码
     */
    private String wmsReturnCode;

    /**
     * wms出库单下发接口返回说明
     */
    private String wmsReturnDesc;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}