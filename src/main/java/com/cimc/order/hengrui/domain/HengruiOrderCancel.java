package com.cimc.order.hengrui.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName hengrui_order_cancel
 */
@TableName(value ="hengrui_order_cancel")
@Data
public class HengruiOrderCancel implements Serializable {
    /**
     * 唯一主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 运单号
     */
    private String clientOrder;

    /**
     * 接口类型（空/0.药品 1.样本）
     */
    private String type;

    /**
     * 三方物流订单号
     */
    private String logisticsOrderNo;

    /**
     * 项目号
     */
    private String itemNumber;

    /**
     * 项目id
     */
    private String itemId;

    /**
     * 取消人
     */
    private String cancelOrderPerson;

    /**
     * 取消人电话
     */
    private String cancelOrderPhone;

    /**
     * 取消理由
     */
    private String cancelReason;

    /**
     * 时间（时间戳）
     */
    private String syncTime;

    /**
     * 中集返回状态码200成功400失败
     */
    private String cimcCode;

    /**
     * 中集返回消息
     */
    private String cimcMsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}